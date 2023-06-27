package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.group16.Controller.LoginMenuController;
import org.group16.Model.User;
import org.group16.StrongholdGame;

import java.util.concurrent.ThreadLocalRandom;

public class LoginScreen extends Menu {
    Table table;
    TextField username, password, captchaField, forgotPasswordAnswer, newPassword;

    TextButton login, register, forgotPassword, newCaptcha, okDialog;
    CheckBox stayLogIn, passwordHide;
    Label usernameLabel, passwordLabel, usernameStatus, passwordStatus, captchaStatus, newPasswordStatus, forgotPasswordAnswerStatus;

    Image background, white, captcha , soilBackground;
    int captchaNumber;

    Dialog forgotPasswordDialog;

    Skin skin2 = new Skin(Gdx.files.internal("neon/skin/default.json"));
    Skin skin1 = new Skin(Gdx.files.internal("neon/skin/monochrome.json"));

    public LoginScreen(StrongholdGame game)  {
        super(game);

        uiStage.clear();
        white = new Image(new Texture(Gdx.files.internal("backgrounds/white.jpg")));
        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg"))) ;
        table = new Table(skin1);
        table.setBackground(soilBackground.getDrawable());
        //table.setColor(Color.BLACK);
        table.setSize(600, 300);
        table.setPosition(uiStage.getWidth() / 2 - table.getWidth() / 2, uiStage.getHeight() / 2 - table.getHeight() / 2);


        username = new TextField("", skin1);
        usernameLabel = new Label("username", skin1);
        usernameStatus = new Label("", skin1);
        usernameStatus.setColor(Color.RED);

        password = new TextField("", skin1);
        password.setPasswordMode(true);
        password.setPasswordCharacter('*');
        passwordHide = new CheckBox("hide", skin1);
        passwordHide.setChecked(true);
        passwordLabel = new Label("password", skin1);
        passwordStatus = new Label("", skin1);
        passwordStatus.setColor(Color.RED);

        login = new TextButton("Login", skin1);
        register = new TextButton("register", skin1);
        forgotPassword = new TextButton("forgot password", skin1);
        stayLogIn = new CheckBox("stay log in", skin1);


        int random = CaptchaBuilder.captchaBuilder();
        captcha = new Image(new Texture(Gdx.files.internal("captcha/" + random + ".png")));
        captchaNumber = random ;
        captchaField = new TextField("", skin1);
        newCaptcha = new TextButton("reload", skin1);
        captchaStatus = new Label("enter captcha", skin1);
        captchaStatus.setColor(Color.RED);

        forgotPasswordDialog = new Dialog("", skin1);
        forgotPasswordDialog.setColor(Color.BLACK);
        forgotPasswordDialog.getTitleLabel().setColor(Color.BLACK);
        forgotPasswordDialog.hide();
        forgotPasswordAnswer = new TextField("", skin1);
        forgotPasswordAnswerStatus = new Label("", skin1);
        forgotPasswordAnswerStatus.setColor(Color.RED);
        newPassword = new TextField("", skin1);
        newPasswordStatus = new Label("", skin1);
        newPasswordStatus.setColor(Color.RED);
        okDialog = new TextButton("ok", skin1);
        forgotPasswordDialog.getContentTable().add(forgotPasswordAnswer).row();
        forgotPasswordDialog.getContentTable().add(forgotPasswordAnswerStatus).row();

        forgotPasswordDialog.getContentTable().add(newPassword).row();
        forgotPasswordDialog.getContentTable().add(newPasswordStatus).row();
        newPassword.setTextFieldListener((textField, c) -> {
            String passwordText = newPassword.getText();
            String status = LoginMenuController.checkPassword(passwordText);
            if (status.equals("OK"))
                newPasswordStatus.setText("");
            else
                newPasswordStatus.setText(status);
        });
        forgotPasswordDialog.getContentTable().add(okDialog);
        okDialog.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String answer = forgotPasswordAnswer.getText();
                User user = User.getUserByName(username.getText());
                if (newPassword.getText().length() == 0) {
                    newPasswordStatus.setText("fill this part");
                    return;
                }
                if (newPasswordStatus.getText().length() != 0)
                    return;
                if (!user.getPasswordRecoveryAnswer().equals(answer)) {
                    forgotPasswordAnswerStatus.setText("wrong answer");
                    return;
                }
                user.setPassword(newPassword.getText());
                forgotPasswordDialog.hide();
            }
        });

        table.add(usernameLabel).pad(0, 0, 0, 5);
        table.add(username).row();
        table.add(usernameStatus).row();
        username.setTextFieldListener((textField, c) -> {
            if (username.getText().length() == 0)
                usernameStatus.setText("fill this part");
            else
                usernameStatus.setText("");
        });
        table.add(passwordLabel).pad(0, 0, 0, 5);
        table.add(password).pad(0, 0, 0, 5);
        table.add(passwordHide).row();
        table.add(passwordStatus).row();
        password.setTextFieldListener((textField, c) -> {
            if (password.getText().length() == 0)
                passwordStatus.setText("fill this part");
            else
                passwordStatus.setText("");
        });
        passwordHide.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                password.setPasswordMode(passwordHide.isChecked());
            }
        });

        table.add(forgotPassword).center();
        forgotPassword.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (User.getUserByName(username.getText()) == null) {
                    usernameStatus.setText("no user found");
                    return;
                }
                forgotPasswordDialog.getTitleLabel().setText(User.getUserByName(username.getText()).getPasswordRecoveryQuestion());
                forgotPasswordDialog.show(uiStage);
            }
        });
        table.add(login).pad(0, 0, 0, 5);
        login.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (username.getText().length() == 0)
                    usernameStatus.setText("fill this part");
                if (password.getText().length() == 0)
                    passwordStatus.setText("fill this part");
                if (usernameStatus.getText().length() != 0 ||
                        usernameStatus.getText().length() != 0 ||
                        passwordStatus.getText().length() != 0 ||
                        captchaStatus.getText().length() != 0
                ) {
                    int  random = CaptchaBuilder.captchaBuilder();
                    captcha.setDrawable(new TextureRegionDrawable(new Texture(Gdx.files.internal("captcha/" + random + ".png"))));
                    captchaNumber = random;
                } else if (User.getUserByName(username.getText()) == null) {
                    usernameStatus.setText("no user found");
                } else {
                    String status = LoginMenuController.loginUser(username.getText(), password.getText(), stayLogIn.isChecked());
                    if (status.equals("OK")) {
                        game.setScreen(new MainScreen(game, User.getUserByName(username.getText())));
                    } else {
                        passwordStatus.setText(status);
                        System.out.println(":" + password.getText() + ":");
                    }
                }
            }
        });
        table.add(stayLogIn).row();
        table.add(register).colspan(3).row();
        register.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new RegisterScreen(game));
            }
        });

        table.add(captcha).pad(0, 0, 0, 5);
        table.add(newCaptcha).row();
        table.add(captchaField).pad(0, 0, 0, 5);
        table.add(captchaStatus).row();
        newCaptcha.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int random = CaptchaBuilder.captchaBuilder();
                captcha.setDrawable(new TextureRegionDrawable(new Texture(Gdx.files.internal("captcha/" + random + ".png"))));
                captchaNumber = random;
            }
        });
        captchaField.setTextFieldListener((textField, c) -> {
            if (!("" + captchaNumber).equals(captchaField.getText()))
                captchaStatus.setText("wrong captcha");
            else
                captchaStatus.setText("");
        });

        background = new Image(new Texture(Gdx.files.internal("backgrounds/loginMenu.jpg")));
        background.setZIndex(0);
        background.setFillParent(true);
        uiStage.addActor(background);
        uiStage.addActor(table);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        uiStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        table.setPosition(uiStage.getWidth() / 2 - table.getWidth() / 2, uiStage.getHeight() / 2 - table.getHeight() / 2);
        uiStage.draw();
    }
}
