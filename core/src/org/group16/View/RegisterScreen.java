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
import org.group16.StrongholdGame;

import java.util.concurrent.ThreadLocalRandom;

import static org.group16.Controller.LoginMenuController.generatePassword;

public class RegisterScreen extends Menu {
    Table table;

    TextField usernameField, passwordField, nicknameField, emailField, passwordQAField, sloganField, captchaField;
    Label usernameLabel, passwordLabel, nicknameLabel, emailLabel, passwordQALabel, sloganLabel;
    Label usernameStatus, passwordStatus, nicknameStatus, emailStatus, passwordQAStatus, sloganStatus, captchaStatus;
    TextButton randomButton, registerButton, newCaptcha;
    CheckBox sloganCheckBox, passwordHideCheckBox;
    SelectBox<String> passwordQASelectBar, sloganSelectBar;
    Image background , white , captcha;
    int captchaNumber ;
    Skin skin2 = new Skin(Gdx.files.internal("neon/skin/default.json"));
    Skin skin1 = new Skin(Gdx.files.internal("neon/skin/monochrome.json"));

    public RegisterScreen(StrongholdGame game) {
        super(game);
        uiStage.clear();
        table = new Table();
        table.setFillParent(true);

        usernameLabel = new Label("username", skin1);
        usernameField = new TextField("", skin1);
        usernameStatus = new Label("", skin1);
        usernameStatus.setColor(Color.RED);

        passwordLabel = new Label("password", skin1);
        passwordField = new TextField("", skin1);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordHideCheckBox = new CheckBox("hide", skin1);
        passwordHideCheckBox.setChecked(true);
        passwordStatus = new Label("", skin1);
        passwordStatus.setColor(Color.RED);
        randomButton = new TextButton("random", skin1);

        nicknameLabel = new Label("nickname", skin1);
        nicknameField = new TextField("", skin1);
        nicknameStatus = new Label("", skin1);
        nicknameStatus.setColor(Color.RED);

        emailLabel = new Label("email", skin1);
        emailField = new TextField("", skin1);
        emailStatus = new Label("", skin1);
        emailStatus.setColor(Color.RED);

        passwordQALabel = new Label("recovery Q", skin1);
        passwordQASelectBar = new SelectBox<>(skin1);
        passwordQASelectBar.setItems("1.What is my father's name?", "2.What was my first pet's name?", "3.What is my mother's last name?");
        passwordQAField = new TextField("", skin1);
        passwordQAStatus = new Label("", skin1);
        passwordQAStatus.setColor(Color.RED);

        sloganCheckBox = new CheckBox("slogan", skin1);
        sloganCheckBox.setChecked(false);
        sloganField = new TextField("", skin1);
        sloganField.setVisible(false);
        sloganSelectBar = new SelectBox<>(skin1);
        sloganSelectBar.setVisible(false);
        sloganSelectBar.setItems("Whatever it takes.", "That's my secret, Captain.", "Revenge, in this life or the next");
        sloganStatus = new Label("", skin1);
        sloganStatus.setColor(Color.RED);

        int random = ThreadLocalRandom.current().nextInt(1000, 10000);
        captchaNumber = random ;
        captcha = new Image(new Texture(Gdx.files.internal("captcha/" + random + ".png")));
        captchaField = new TextField("", skin1);
        newCaptcha = new TextButton("reload", skin1);
        captchaStatus = new Label("enter captcha" , skin1);
        captchaStatus.setColor(Color.RED);

        registerButton = new TextButton("register" , skin1);

        table.add(usernameLabel).pad(0, 0, 0, 5);
        table.add(usernameField).pad(0, 0, 0, 5);
        table.add(usernameStatus).row();

        usernameField.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                String usernameText = usernameField.getText() ;
                String status = LoginMenuController.checkUsername(usernameText) ;
                if (status.equals("OK"))
                    usernameStatus.setText("");
                else
                    usernameStatus.setText(status);
            };
        });

        table.add(passwordLabel).pad(0, 0, 0, 5);
        table.add(passwordField).pad(0, 0, 0, 5);
        table.add(passwordHideCheckBox).pad(0, 0, 0, 5);
        table.add(randomButton).row();
        table.add(passwordStatus).colspan(4).row();

        passwordHideCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                passwordField.setPasswordMode(passwordHideCheckBox.isChecked());
            }
        });
        passwordField.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                String passwordText = passwordField.getText() ;
                String status = LoginMenuController.checkPassword(passwordText);
                if (status.equals("OK"))
                    passwordStatus.setText("");
                else
                    passwordStatus.setText(status);
            }
        });
        randomButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                passwordField.setPasswordMode(false);
                passwordField.setText(generatePassword());
                passwordStatus.setText("");
            }
        });

        table.add(nicknameLabel).pad(0, 0, 0, 5);
        table.add(nicknameField).pad(0, 0, 0, 5);
        table.add(nicknameStatus).row();

        nicknameField.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if (nicknameField.getText().length()>0)
                    nicknameStatus.setText("");
                else
                    nicknameStatus.setText("fill this part");
            }
        });

        table.add(emailLabel).pad(0, 0, 0, 5);
        table.add(emailField).pad(0, 0, 0, 5);
        table.add(emailStatus).row();

        emailField.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                String emailText = emailField.getText() ;
                String status = LoginMenuController.checkEmail(emailText) ;
                if (status.equals("OK"))
                    emailStatus.setText("");
                else
                    emailStatus.setText(status);
            }
        });

        table.add(passwordQALabel).pad(0, 0, 0, 5);
        table.add(passwordQASelectBar).pad(0, 0, 0, 5);
        table.add(passwordQAField).pad(0, 0, 0, 5);
        table.add(passwordQAStatus).row();

        passwordQAField.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if (passwordQAField.getText().length()>0)
                    passwordQAStatus.setText("");
                else
                    passwordQAStatus.setText("fill this part");
            }
        });

        table.add(sloganCheckBox).pad(0, 0, 0, 5);
        table.add(sloganField).pad(0, 0, 0, 5);
        table.add(sloganSelectBar).pad(0 ,0 , 0,5);
        table.add(sloganStatus).row();

        sloganField.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if (sloganCheckBox.isChecked()&&sloganField.getText().length() == 0)
                    sloganStatus.setText("fill this part");
                else
                    sloganStatus.setText("");
            }
        });
        sloganCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (sloganCheckBox.isChecked()){
                    sloganField.setVisible(true);
                    sloganSelectBar.setVisible(true);
                }
                else{
                    sloganField.setVisible(false);
                    sloganSelectBar.setVisible(false);
                }
            }
        });
        sloganSelectBar.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sloganField.setText(sloganSelectBar.getSelected());
            }
        });

        table.add(captcha).pad(0, 0, 0, 5);
        table.add(newCaptcha).pad(0 , 0 , 0 , 5);
        table.add(captchaField).pad(0, 0, 0, 5);
        table.add(captchaStatus).row();

        newCaptcha.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int random = ThreadLocalRandom.current().nextInt(1000, 10000);
                captcha.setDrawable(new TextureRegionDrawable(new Texture(Gdx.files.internal("captcha/" + random + ".png"))));
                captchaNumber = random ;
            }
        });
        captchaField.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if (!(""+captchaNumber).equals(captchaField.getText()))
                    captchaStatus.setText("wrong captcha");
                else
                    captchaStatus.setText("");
            }
        });

        table.add(registerButton).colspan(4);

        registerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (usernameField.getText().length()==0)
                    usernameStatus.setText("fill this part");
                if (passwordField.getText().length()==0)
                    passwordStatus.setText("fill this part");
                if (nicknameField.getText().length()==0)
                    nicknameStatus.setText("fill this part");
                if (emailField.getText().length()==0)
                    emailStatus.setText("fill this part");
                if (passwordQAField.getText().length()==0)
                    passwordQAStatus.setText("fill this part");
                if (sloganCheckBox.isChecked() && sloganField.getText().length()==0)
                    sloganStatus.setText("fill this part");
                if (usernameStatus.getText().length()!=0||
                        passwordStatus.getText().length()!=0||
                        nicknameStatus.getText().length()!=0||
                        emailStatus.getText().length()!=0||
                        passwordStatus.getText().length()!=0||
                        sloganStatus.getText().length()!=0||
                        captchaStatus.getText().length()!=0
                )
                    return;
                else {
                    LoginMenuController.createUser(usernameField.getText() , passwordField.getText() , passwordField.getText() ,
                            emailField.getText() , nicknameField.getText() , sloganField.getText() ,
                            passwordQASelectBar.getSelected() , passwordQAField.getText());
                    game.setScreen(new LoginScreen(game));
                }
            }
        });

        background = new Image(new Texture(Gdx.files.internal("backgrounds/registerMenu.jpg")));
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
