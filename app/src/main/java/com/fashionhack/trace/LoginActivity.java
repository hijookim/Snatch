package com.fashionhack.trace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pinterest.android.pdk.PDKCallback;
import com.pinterest.android.pdk.PDKClient;
import com.pinterest.android.pdk.PDKException;
import com.pinterest.android.pdk.PDKResponse;
import com.pinterest.android.pdk.PDKUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {

    private static final String APP_ID = "123";
    public static final String USER_KEY = "user";
    public static final String USER_PROFILE_URL = "profile_url";
    public static final String USER_FIRST_NAME = "profile_first_name";

    public static PDKClient pdkClient;
    private Context context;

    @Bind(R.id.landing_layout) RelativeLayout landingLayout;
    @Bind(R.id.pinterest_login_button) Button pinterestLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        landingLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.snatch_2));

        PDKClient.configureInstance(this, APP_ID);
        pdkClient = PDKClient.getInstance();
        pdkClient.onConnect(this);
        context = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.pinterest_login_button)
    protected void onPinterestLoginClicked() {
        List<String> scopes = new ArrayList<>();
        scopes.add(PDKClient.PDKCLIENT_PERMISSION_READ_PUBLIC);
        scopes.add(PDKClient.PDKCLIENT_PERMISSION_WRITE_PUBLIC);

        pdkClient.login(this, scopes, new PDKCallback() {
            @Override
            public void onSuccess(PDKResponse response) {
                Log.d(getClass().getName(), response.getData().toString());
                //user logged in, use response.getUser() to get PDKUser object
                startActivityForUserBoards(response.getUser());
            }

            @Override
            public void onFailure(PDKException exception) {
                Log.e(getClass().getName(), exception.getDetailMessage());
                Toast.makeText(context, exception.getDetailMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startActivityForUserBoards(PDKUser user) {
        String userProfileUrl = user.getImageUrl();
        String userFirstName = user.getFirstName();
        Intent intent = new Intent(this, UserBoardActivity.class);
        intent.putExtra(USER_PROFILE_URL, userProfileUrl);
        intent.putExtra(USER_FIRST_NAME, userFirstName);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PDKClient.getInstance().onOauthResponse(requestCode, resultCode, data);
    }
}
