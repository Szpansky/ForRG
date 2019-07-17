package com.apps.mkacik.forRG.LaunchPads;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.mkacik.forRG.App.App;
import com.apps.mkacik.forRG.DataModel.LaunchpadModel;
import com.apps.mkacik.forRG.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchPadsAdapter extends RecyclerView.Adapter<LaunchPadsAdapter.ViewHolder> {
    private List<LaunchpadModel> launchpadList;
    private ClicksCallBack clicksCallBack;

    public void setOnClicksListener(ClicksCallBack clicksListener) {
        this.clicksCallBack = clicksListener;
    }

    interface ClicksCallBack {
        void onItemClick(LaunchpadModel launchpadModel);
    }

    LaunchPadsAdapter(List<LaunchpadModel> launchpadList) {
        this.launchpadList = launchpadList;
    }

    void updateLaunchpadsList(List<LaunchpadModel> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new LaunchPadsDiffUtil(this.launchpadList, newList));
        this.launchpadList.clear();
        this.launchpadList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.launchpad, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(launchpadList.get(i));
    }

    @Override
    public int getItemCount() {
        return launchpadList.size();
    }

    @Override
    public long getItemId(int position) {
        return launchpadList.get(position).getId();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.id)
        TextView id;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.thumbnail)
        ImageView thumbnail;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.status_background)
        ImageView statusBackground;
        @BindView(R.id.layout)
        View layout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final LaunchpadModel launchpadModel) {
            id.setText(String.valueOf(launchpadModel.getId()));
            name.setText(launchpadModel.getLocation().getName());
            name.setSelected(true);
            status.setText(launchpadModel.getStatus());

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicksCallBack.onItemClick(launchpadModel);
                }
            });

            switch (launchpadModel.getStatus()) {
                case LaunchpadModel.ACTIVE: {
                    statusBackground.setImageDrawable(App.getContext().getResources().getDrawable(R.drawable.active_circle));
                }
                break;
                case LaunchpadModel.RETIRED: {
                    statusBackground.setImageDrawable(App.getContext().getResources().getDrawable(R.drawable.retired_circle));
                }
                break;
                default: {
                    statusBackground.setImageDrawable(App.getContext().getResources().getDrawable(R.drawable.retired_circle));
                }
            }

            Glide.with(thumbnail)
                    .load("https://reality.co/wp-content/uploads/2018/10/Reality_ColorDarkBG.png")
                    .apply(RequestOptions.circleCropTransform())
                    .apply(RequestOptions.fitCenterTransform())
                    .into(thumbnail);

            scrapImage(launchpadModel.getWikipediaURL(), thumbnail);
        }
    }

    /**
     * scrapImage, allow to get first image from url and load it into imageView
     * Network connection needs to be on new thread
     * Glide is need to be on "main" thread,
     * better way is to get bitmap and bitmap = imageView to perform application,
     * but there will be problem with circle image
     *
     * @param url       site to be scrapped
     * @param intoImage some imageView for scrapped image
     */
    //TODO improve glide loading. Read comment description for more details
    private void scrapImage(final String url, final ImageView intoImage) {

        final Handler mainHandler = new Handler(Looper.getMainLooper());

        try {
            final Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {

                    try {
                        Document doc = Jsoup.connect(url).get();
                        Document document = Jsoup.parse(doc.html());
                        final Elements images = document.select("img");

                        Runnable myRunnable = new Runnable() {
                            @Override
                            public void run() {

                                Glide.with(intoImage).asBitmap().load("https:/" + images.get(0).attr("src")).centerCrop().into(new BitmapImageViewTarget(intoImage) {
                                    @Override
                                    protected void setResource(Bitmap resource) {
                                        RoundedBitmapDrawable circularBitmapDrawable =
                                                RoundedBitmapDrawableFactory.create(App.getContext().getResources(), resource);
                                        circularBitmapDrawable.setCircular(true);
                                        intoImage.setImageDrawable(circularBitmapDrawable);
                                    }
                                });
                            }
                        };
                        mainHandler.post(myRunnable);

                    } catch (IOException e) {
                        e.printStackTrace();
                        mainHandler.removeCallbacksAndMessages(null);
                    }
                }
            });
            thread.start();

        } catch (IllegalArgumentException t) {
            t.printStackTrace();
            mainHandler.removeCallbacksAndMessages(null);
        }
    }

}