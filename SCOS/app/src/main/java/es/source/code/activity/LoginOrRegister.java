package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.MalformedInputException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.source.code.model.User;

/**
 *
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
    boolean flag = false;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
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

        //判断是否有用户名记录
        sharedPreferences = this.getSharedPreferences("userName",MODE_WORLD_READABLE);
        editor = sharedPreferences.edit();
        final String userNamed = sharedPreferences.getString("userName",null);
        if(userNamed == null){
            //无记录
            login.setVisibility(View.GONE);
        }
        if(userNamed != null){
            userNameEt.setText(userNamed);
            register.setVisibility(View.GONE);

        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = userNameEt.getText().toString();
                password = passwordEt.getText().toString();

                //使用HttpURLConnection访问SCOSSever的Servlet类LoginValidator
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        flag = checkUserOnServlet(userName,password);
                    }
                }){

                }.start();
                if(flag)
                {
                    Log.i("LoginOrRegister",userName);
                    if(userName.isEmpty() || !check(userName)){
                        userNameEt.setError("输入内容不符合规则");
                        return;
                    }else if(password.isEmpty() || !check(password)){
                        passwordEt.setError("输入内容不符合规则");
                        return;
                    }else{

                        editor.putString("userName",userName);//用户名写入SharedPreferences
                        editor.putInt("loginState",1);
                        editor.commit();

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
                        Toast.makeText(getBaseContext(),"服务器验证用户成功",Toast.LENGTH_LONG).show();
                    }
                }



            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userNamed != null){
                    editor.putInt("loginState",0);
                }
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

                    editor.putString("userName",userName);//用户名写入SharedPreferences
                    editor.putInt("loginState",1);
                    editor.commit();

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
    public static boolean checkUserOnServlet(String userName,String password){
        //将用户名密码放入HashMap中
        String path = "http://192.168.100.109:8080/SCOSSever/";
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("userName",userName);
        params.put("password",password);
        try{
            return sendPostRequest(path,params,"UTF-8");
        } catch (MalformedURLException me){
            me.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }
    public static boolean sendPostRequest(String path,HashMap<String,String> params,String encode) throws MalformedURLException,IOException{

        StringBuilder url = new StringBuilder(path);
        url.append("?");
        for(Map.Entry<String,String> entry:params.entrySet()){
            url.append(entry.getKey()).append("=");
            url.append(URLEncoder.encode(entry.getValue(),encode));
            url.append("&");
        }
        //删除掉最后一个&
        url.deleteCharAt(url.length()-1);
        HttpURLConnection con = (HttpURLConnection)new URL(url.toString()).openConnection();
        con.setConnectTimeout(5000);
        con.setRequestMethod("POST");
        if(con.getResponseCode() == 200){
            return true;
        }
        else{
            return false;
        }
    }


    public static boolean check(String str){
        String regex = "^[a-zA-Z0-9]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
