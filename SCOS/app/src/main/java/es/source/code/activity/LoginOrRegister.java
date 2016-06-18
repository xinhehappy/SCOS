package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.source.code.model.User;

/**
 * Created by Administrator on 2016/6/15.
 */
public class LoginOrRegister extends Activity{

    public String userName;
    public String password;
    Button login = null;
    Button back = null;
    Button register = null;
    EditText userNameEt = null;
    EditText passwordEt = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_or_register);
        init();
    }
    private void init(){
        login = (Button)findViewById(R.id.login_bt);
        back = (Button)findViewById(R.id.back_bt);
        register = (Button)findViewById(R.id.register_bt);
        userNameEt = (EditText)findViewById(R.id.login_name);
        passwordEt = (EditText)findViewById(R.id.login_password);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = userNameEt.getText().toString();
                password = passwordEt.getText().toString();
                Log.i("LoginOrRegister",userName);
                if(userName.isEmpty() || !check(userName)){
                    userNameEt.setError("输入内容不符合规则");
                    return;
                }else if(password.isEmpty() || !check(password)){
                    passwordEt.setError("输入内容不符合规则");
                    return;
                }else{
                    User loginUser = new User();
                    loginUser.setUserName(userName);
                    loginUser.setPassword(password);
                    loginUser.setOldUser(true);
                    Intent intent = new Intent(LoginOrRegister.this,MainScreen.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("loginUser",loginUser);
                    intent.putExtra("value","LoginSuccess");
                    intent.putExtras(bundle);
                    LoginOrRegister.this.startActivity(intent);
                    LoginOrRegister.this.finish();
                }

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginOrRegister.this,MainScreen.class);
                intent.putExtra("value","Return");
                LoginOrRegister.this.startActivity(intent);
                LoginOrRegister.this.finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = userNameEt.getText().toString();
                password = passwordEt.getText().toString();
                Log.i("LoginOrRegister",userName);
                if(userName.isEmpty() || !check(userName)){
                    userNameEt.setError("输入内容不符合规则");
                    return;
                }else if(password.isEmpty() || !check(password)){
                    passwordEt.setError("输入内容不符合规则");
                    return;
                }else{
                    User loginUser = new User();
                    loginUser.setUserName(userName);
                    loginUser.setPassword(password);
                    loginUser.setOldUser(false);
                    Intent intent = new Intent(LoginOrRegister.this,MainScreen.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("loginUser",loginUser);
                    intent.putExtra("value","RegisterSuccess");
                    intent.putExtras(bundle);
                    LoginOrRegister.this.startActivity(intent);
                    LoginOrRegister.this.finish();
                }
            }
        });
    }
    public static boolean check(String str){
        String regex = "^[a-zA-Z0-9]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
