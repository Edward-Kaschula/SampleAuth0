package com.play.sampleauth0;

import android.content.Context;
import android.os.Bundle;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.storage.CredentialsManager;
import com.auth0.android.authentication.storage.SharedPreferencesStorage;
import com.auth0.android.lock.AuthenticationCallback;
import com.auth0.android.lock.InitialScreen;
import com.auth0.android.lock.Lock;
import com.auth0.android.lock.LockCallback;
import com.auth0.android.lock.utils.LockException;
import com.auth0.android.result.Credentials;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    Auth0 auth0;
    CredentialsManager manager;
    AuthenticationAPIClient client;
    private Lock lock;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.context = this;
        this.auth0 = new Auth0(context);
        this.auth0.setOIDCConformant(true);

        this.client = new AuthenticationAPIClient(auth0);
        this.manager = new CredentialsManager(client, new SharedPreferencesStorage(context));

        if (!manager.hasValidCredentials()) {
            lock = Lock.newBuilder(auth0, callback)
                    .setPrivacyURL("https://www.test.com")
                    .setTermsURL("https://www.test.com")
                    .withScope("openid profile read:current_user offline_access")
                    .setDefaultDatabaseConnection("Username-Password-Authentication")
                    .closable(false)
                    .allowSignUp(true)
                    .allowLogIn(true)
                    .loginAfterSignUp(true)
                    .initialScreen(InitialScreen.LOG_IN)
                    .withAudience(String.format("https://%s/api/v2/", getString(R.string.com_auth0_domain)))
                    .build(context);
            startActivity(lock.newIntent(context));
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private LockCallback callback = new AuthenticationCallback() {
        @Override
        public void onAuthentication(Credentials credentials) {
            manager.saveCredentials(credentials);
        }

        @Override
        public void onCanceled() {

        }

        @Override
        public void onError(LockException error){

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
