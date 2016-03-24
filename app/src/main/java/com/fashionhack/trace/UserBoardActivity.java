package com.fashionhack.trace;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fashionhack.trace.R;
import com.pinterest.android.pdk.PDKBoard;
import com.pinterest.android.pdk.PDKCallback;
import com.pinterest.android.pdk.PDKClient;
import com.pinterest.android.pdk.PDKException;
import com.pinterest.android.pdk.PDKResponse;
import com.pinterest.android.pdk.PDKUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserBoardActivity extends AppCompatActivity {

    private Context context;
    private ImageAdapter imageAdapter;
    private List<Integer> selectedBoardPositions = new ArrayList<>();
    private List<PDKBoard> boardList;

    @Bind(R.id.profile_photo) ImageView profilePhotoImageView;
    @Bind(R.id.profile_name) TextView profileNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_board);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        context = this;
        String name = intent.getStringExtra(LoginActivity.USER_FIRST_NAME);
        name = name + "'s Boards";
        profileNameTextView.setText(name);

        fetchPinterestBoard();
        fetchUserInfo();
        initializeGridView();
    }

    private void fetchPinterestBoard() {
        LoginActivity.pdkClient.getMyBoards("id,name,url,image,description", new PDKCallback() {
            @Override
            public void onSuccess(PDKResponse response) {
                Log.d(getClass().getName(), response.getData().toString());
                boardList = response.getBoardList();
                imageAdapter.setPinterstBoards(boardList);
                imageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(PDKException exception) {
                Log.e(getClass().getName(), exception.getDetailMessage());
                Toast.makeText(context, exception.getDetailMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUserInfo() {
        LoginActivity.pdkClient.getMe(new PDKCallback() {
            @Override
            public void onSuccess(PDKResponse response) {
                Log.d(getClass().getName(), response.getData().toString());
                PDKUser user = response.getUser();
                String profilePhotoUrl = user.getImageUrl();
                Picasso.with(context).load(profilePhotoUrl).into(profilePhotoImageView);
                profileNameTextView.setText(user.getFirstName());
            }

            @Override
            public void onFailure(PDKException exception) {
                Log.e(getClass().getName(), exception.getDetailMessage());
                Toast.makeText(context, exception.getDetailMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeGridView() {
        GridView gridview = (GridView) findViewById(R.id.gridview);
        imageAdapter = new ImageAdapter(this);
        gridview.setAdapter(imageAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (!selectedBoardPositions.contains(position)) {
                    selectedBoardPositions.add(position);
                    v.setBackgroundDrawable(getDrawable(R.drawable.grid_item_border));
                    v.getBackground().setAlpha(100);
                } else {
                    int index = selectedBoardPositions.indexOf(position);
                    selectedBoardPositions.remove(index);
                    v.getBackground().setAlpha(0);
                }
            }
        });
    }

    @OnClick(R.id.next_button)
    protected void onNextButtonClick() {
        List<String> selectedBoardIds = new ArrayList<>();
        for (Integer position: selectedBoardPositions) {
            PDKBoard board = boardList.get(position);
            selectedBoardIds.add(board.getUid());
        }
        LoginActivity.pdkClient.getBoardPins(selectedBoardIds.get(0), "name,image",
                new PDKCallback() {
                    @Override
                    public void onSuccess(PDKResponse response) {
                        Log.d(getClass().getName(), response.getData().toString());
                        PDKUser user = response.getUser();
                        String profilePhotoUrl = user.getImageUrl();
                    }

                    @Override
                    public void onFailure(PDKException exception) {
                        Log.e(getClass().getName(), exception.getDetailMessage());
                        Toast.makeText(context, exception.getDetailMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    @OnClick(R.id.profile_name)
    public void showNotification() {
        String title1 = "Snatch your favorite shirt!";
        String message1 = "A shirt you've liked is available in this store!";
        Intent notifyIntent = new Intent(this, ClothingArticleActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title1)
                .setContentText(message1)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private List<PDKBoard> pinterstBoards = new ArrayList<>();

        private List<String> imageUrlList = new ArrayList<>();
        private List<String> boardNameList = new ArrayList<>();

        public ImageAdapter(Context c) {
            mContext = c;
            imageUrlList.add("https://s-media-cache-ak0.pinimg.com/564x/0e/c4/5d/0ec45d86c6b36a255861057121bce7a4.jpg");
            imageUrlList.add("https://s-media-cache-ak0.pinimg.com/564x/6e/49/d4/6e49d40f0532ff72ac98356625708518.jpg");
            imageUrlList.add("https://s-media-cache-ak0.pinimg.com/564x/86/2c/50/862c50e03b40d179a724ca9f20d62615.jpg");
            imageUrlList.add("https://s-media-cache-ak0.pinimg.com/564x/6b/9d/2f/6b9d2f97559b5c7a02aa058527b8e79c.jpg");
            imageUrlList.add("https://s-media-cache-ak0.pinimg.com/564x/af/65/77/af6577efe9c0dfd921f03127cc7f6cb8.jpg");
            imageUrlList.add("https://s-media-cache-ak0.pinimg.com/474x/23/ba/3c/23ba3c419d0ed5201bdaa4ac9df8c080.jpg");
            imageUrlList.add("https://s-media-cache-ak0.pinimg.com/564x/1f/f3/e3/1ff3e3ef026948bce008ecac892b6c59.jpg");
            imageUrlList.add("https://s-media-cache-ak0.pinimg.com/564x/ee/9a/63/ee9a63607751a16e440e180d0d8f807b.jpg");

            boardNameList.add("Fall Fashion");
            boardNameList.add("Dressy");
            boardNameList.add("Jackets");
            boardNameList.add("Night out");
            boardNameList.add("Summer");
            boardNameList.add("Casual chic");
            boardNameList.add("Casual chic");
            boardNameList.add("Casual chic");
        }

        public void setPinterstBoards(List<PDKBoard> pinterstBoards) {
            this.pinterstBoards = pinterstBoards;
        }

        public int getCount() {
            return imageUrlList.size();
        }

        public Object getItem(int position) {
            return imageUrlList.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View gridView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(180, 240));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(0, 0, 0, 0);

                // get layout from mobile.xml
                gridView = inflater.inflate(R.layout.board_item, null);

            } else {
                //imageView = (ImageView) convertView;
                gridView = (View) convertView;
            }

            ImageView boardItemImgView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);

            String imageUrlStr = imageUrlList.get(position);
            Picasso.with(context).load(imageUrlStr).into(boardItemImgView);

            TextView boardItemTextView = (TextView) gridView.findViewById(R.id.grid_item_name);
            boardItemTextView.setText(boardNameList.get(position));
            return gridView;
        }
    }
}
