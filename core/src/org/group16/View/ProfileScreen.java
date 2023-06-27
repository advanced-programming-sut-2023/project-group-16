package org.group16.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import games.spooky.gdx.nativefilechooser.NativeFileChooserCallback;
import org.group16.Controller.ProfileMenuController;
import org.group16.Model.User;
import org.group16.StrongholdGame;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class ProfileScreen extends Menu {
    private final User user;
    private final Image background, white;
    private final Skin skin1 = new Skin(Gdx.files.internal("neon/skin/default.json"));
    private final Skin skin2 = new Skin(Gdx.files.internal("neon/skin/monochrome.json"));
    private Image captcha, avatar;
    private int captchaNumber;
    private Table table;
    private Label username, nickname, email, slogan, newUsernameError, newNicknameError, newEmailError, newSloganError,
            oldPasswordError, newPasswordError, captchaError;
    private TextButton changeAvatar, changeUsername, changeNickname, changeEmail, changeSlogan, removeSlogan, changePassword,
            saveUsername, saveNickname, saveEmail, saveSlogan, savePassword, captchaReload, backButton;
    private Dialog changeUsernameDialog, changeNicknameDialog, changeEmailDialog,
            changeSloganDialog, changePasswordDialog;
    private TextField newUsername, newNickname, newEmail, newSlogan, oldPassword, newPassword, captchaField;

    private SelectBox<String> selectAvatar;

    private Window scoreBoardWindow;

    public ProfileScreen(StrongholdGame game, User user) {
        super(game);
        this.user = user;

        uiStage.clear();
        white = new Image(new Texture(Gdx.files.internal("backgrounds/white.jpg")));
        createAvatar();
        createUsername();
        createNickname();
        createEmail();
        createSlogan();
        createPassword();
        createBack();

        scoreBoardWindow = new ScoreBoardWindow(skin2, user);
        scoreBoardWindow.setMovable(true);

        background = new Image(new Texture(Gdx.files.internal("backgrounds/profileMenu.jpg")));
        background.setZIndex(0);
        background.setFillParent(true);
        uiStage.addActor(background);
        createTable();
        uiStage.addActor(table);
        uiStage.addActor(scoreBoardWindow);
    }

    private void showSuccess(String message) {
        Label text = new Label(message, skin1);
        text.setColor(Color.GREEN);

        Dialog success = new Dialog("", skin1);
        success.setColor(Color.WHITE);
        success.button("Close");
        success.getContentTable().add(text).center().row();
        success.show(uiStage);
    }

    private void createAvatar() {
        avatar = new Image(picChange.changer(user.getAvatarPicture(), 60, 60));

        changeAvatar = new TextButton("Change Avatar", skin1);

        FileHandle dir = new FileHandle("assets/mainPfp");
        selectAvatar = new SelectBox<>(skin2);
        Array<String> picNames = new Array<>();
        picNames.add("Others");
        try {
            for (FileHandle fileHandle : dir.list())
                picNames.add(fileHandle.name().substring(0, fileHandle.name().length() - 4));
            selectAvatar.setItems(picNames);
        } catch (Exception e) {
            System.out.println("Exception in file handling");
        }

        changeAvatar.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!selectAvatar.getSelected().equals("Others")) {
                    user.setAvatarPicture("mainPfp/" + selectAvatar.getSelected() + ".jpg");
                    try {
                        avatar.setDrawable(new TextureRegionDrawable(picChange.changer(user.getAvatarPicture(), 60, 60)));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    StrongholdGame.fileChooser.chooseFile(StrongholdGame.fileChooserConfiguration, new NativeFileChooserCallback() {
                        @Override
                        public void onFileChosen(FileHandle file) {
                            if (file.name().endsWith(".png") || file.name().endsWith(".jpg")) {
                                user.setAvatarPicture(file.path());
                                try {
                                    avatar.setDrawable(new TextureRegionDrawable(picChange.changer(user.getAvatarPicture(), 60, 60)));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onCancellation() {

                        }

                        @Override
                        public void onError(Exception exception) {

                        }
                    });
                }
            }
        });
    }

    private void createUsername() {
        username = new Label("Username: " + user.getUsername(), skin1);

        changeUsername = new TextButton("Change Username", skin1);
        changeUsername.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeUsernameDialog.show(uiStage);
                changeUsernameDialog.setSize(600, 300);
                changeUsernameDialog.setPosition(uiStage.getWidth() / 2 - changeUsernameDialog.getWidth() / 2,
                        uiStage.getHeight() / 2 - changeUsernameDialog.getHeight() / 2);
                newUsernameError.setText(ProfileMenuController.checkChangeUsername(user, newUsername.getText()));
            }
        });

        newUsername = new TextField("", skin1);

        newUsernameError = new Label("", skin1);
        newUsernameError.setColor(Color.RED);

        saveUsername = new TextButton("Save", skin1);

        changeUsernameDialog = new Dialog("", skin1);
        changeUsernameDialog.button("Cancel");
        changeUsernameDialog.setColor(Color.WHITE);
        changeUsernameDialog.getTitleLabel().setColor(Color.BLACK);
        changeUsernameDialog.hide();
        changeUsernameDialog.getContentTable().add(newUsername).center().row();
        changeUsernameDialog.getContentTable().add(newUsernameError).center().row();
        changeUsernameDialog.getContentTable().add(saveUsername).center();

        newUsername.setTextFieldListener((textField, c) -> {
            String username = newUsername.getText();
            String status = ProfileMenuController.checkChangeUsername(user, username);
            newUsernameError.setText(status);
        });

        saveUsername.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (newUsernameError.getText().isEmpty()) {
                    ProfileMenuController.changeUsername(user, newUsername.getText());
                    username.setText("Username: " + user.getUsername());
                    changeUsernameDialog.hide();
                    showSuccess("Username changed successfully");
                }
            }
        });
    }

    private void createNickname() {
        nickname = new Label("Nickname: " + user.getNickname(), skin1);

        changeNickname = new TextButton("Change Nickname", skin1);
        changeNickname.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeNicknameDialog.show(uiStage);
                changeNicknameDialog.setSize(600, 300);
                changeNicknameDialog.setPosition(uiStage.getWidth() / 2 - changeNicknameDialog.getWidth() / 2,
                        uiStage.getHeight() / 2 - changeNicknameDialog.getHeight() / 2);
                newNicknameError.setText(ProfileMenuController.checkChangeNickname(user, newNickname.getText()));
            }
        });

        newNickname = new TextField("", skin1);

        newNicknameError = new Label("", skin1);
        newNicknameError.setColor(Color.RED);

        saveNickname = new TextButton("Save", skin1);

        changeNicknameDialog = new Dialog("", skin1);
        changeNicknameDialog.button("Cancel");
        changeNicknameDialog.setColor(Color.WHITE);
        changeNicknameDialog.getTitleLabel().setColor(Color.BLACK);
        changeNicknameDialog.hide();
        changeNicknameDialog.getContentTable().add(newNickname).center().row();
        changeNicknameDialog.getContentTable().add(newNicknameError).center().row();
        changeNicknameDialog.getContentTable().add(saveNickname).center();

        newNickname.setTextFieldListener((textField, c) -> {
            String nickname = newNickname.getText();
            String status = ProfileMenuController.checkChangeNickname(user, nickname);
            newNicknameError.setText(status);
        });

        saveNickname.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (newNicknameError.getText().isEmpty()) {
                    ProfileMenuController.changeNickname(user, newNickname.getText());
                    nickname.setText("Nickname: " + user.getNickname());
                    changeNicknameDialog.hide();
                    showSuccess("Nickname changed successfully");
                }
            }
        });
    }

    private void createEmail() {
        email = new Label("Email: " + user.getEmail(), skin1);

        changeEmail = new TextButton("Change Email", skin1);
        changeEmail.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeEmailDialog.show(uiStage);
                changeEmailDialog.setSize(600, 300);
                changeEmailDialog.setPosition(uiStage.getWidth() / 2 - changeEmailDialog.getWidth() / 2,
                        uiStage.getHeight() / 2 - changeEmailDialog.getHeight() / 2);
                newEmailError.setText(ProfileMenuController.checkChangeEmail(user, newEmail.getText()));
            }
        });

        newEmail = new TextField("", skin1);

        newEmailError = new Label("", skin1);
        newEmailError.setColor(Color.RED);

        saveEmail = new TextButton("Save", skin1);

        changeEmailDialog = new Dialog("", skin1);
        changeEmailDialog.button("Cancel");
        changeEmailDialog.setColor(Color.WHITE);
        changeEmailDialog.getTitleLabel().setColor(Color.BLACK);
        changeEmailDialog.hide();
        changeEmailDialog.getContentTable().add(newEmail).center().row();
        changeEmailDialog.getContentTable().add(newEmailError).center().row();
        changeEmailDialog.getContentTable().add(saveEmail).center();

        newEmail.setTextFieldListener((textField, c) -> {
            String email = newEmail.getText();
            String status = ProfileMenuController.checkChangeEmail(user, email);
            newEmailError.setText(status);
        });

        saveEmail.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (newEmailError.getText().isEmpty()) {
                    ProfileMenuController.changeEmail(user, newEmail.getText());
                    email.setText("Email: " + user.getEmail());
                    changeEmailDialog.hide();
                    showSuccess("Email changed successfully");
                }
            }
        });
    }

    private void createSlogan() {
        slogan = new Label("Slogan: " +
                (user.getSlogan().isEmpty() ? "Slogan is empty" : user.getSlogan()), skin1);

        removeSlogan = new TextButton("Remove Slogan", skin1);
        removeSlogan.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ProfileMenuController.removeSlogan(user);
                slogan.setText("Slogan: Slogan is empty");
            }
        });

        changeSlogan = new TextButton("Change Slogan", skin1);
        changeSlogan.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeSloganDialog.show(uiStage);
                changeSloganDialog.setSize(600, 300);
                changeSloganDialog.setPosition(uiStage.getWidth() / 2 - changeSloganDialog.getWidth() / 2,
                        uiStage.getHeight() / 2 - changeSloganDialog.getHeight() / 2);
                newSloganError.setText(ProfileMenuController.checkChangeSlogan(user, newSlogan.getText()));
            }
        });

        newSlogan = new TextField("", skin1);

        newSloganError = new Label("", skin1);
        newSloganError.setColor(Color.RED);

        saveSlogan = new TextButton("Save", skin1);

        changeSloganDialog = new Dialog("", skin1);
        changeSloganDialog.button("Cancel");
        changeSloganDialog.setColor(Color.WHITE);
        changeSloganDialog.getTitleLabel().setColor(Color.BLACK);
        changeSloganDialog.hide();
        changeSloganDialog.getContentTable().add(newSlogan).center().row();
        changeSloganDialog.getContentTable().add(newSloganError).center().row();
        changeSloganDialog.getContentTable().add(saveSlogan).center();

        newSlogan.setTextFieldListener((textField, c) -> {
            String slogan = newSlogan.getText();
            String status = ProfileMenuController.checkChangeSlogan(user, slogan);
            newSloganError.setText(status);
        });

        saveSlogan.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (newSloganError.getText().isEmpty()) {
                    ProfileMenuController.changeSlogan(user, newSlogan.getText());
                    slogan.setText("Slogan: " + user.getSlogan());
                    changeSloganDialog.hide();
                    showSuccess("Slogan changed successfully");
                }
            }
        });
    }

    private void createPassword() {
        changePassword = new TextButton("Change Password", skin1);
        changePassword.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changePasswordDialog.show(uiStage);
                changePasswordDialog.setSize(600, 300);
                changePasswordDialog.setPosition(uiStage.getWidth() / 2 - changePasswordDialog.getWidth() / 2,
                        uiStage.getHeight() / 2 - changePasswordDialog.getHeight() / 2);
                newPasswordError.setText(ProfileMenuController.isPasswordWeak(newPassword.getText()));
            }
        });

        oldPassword = new TextField("", skin1);
        oldPassword.setPasswordMode(true);
        oldPassword.setPasswordCharacter('*');

        newPassword = new TextField("", skin1);
        newPassword.setPasswordMode(true);
        newPassword.setPasswordCharacter('*');
        newPassword.setTextFieldListener((textField, c) -> {
            String status = ProfileMenuController.isPasswordWeak(newPassword.getText());
            newPasswordError.setText(status);
        });

        oldPasswordError = new Label("", skin1);
        newPasswordError = new Label("", skin1);

        generateCaptcha();
        captchaField = new TextField("", skin1);
        captchaError = new Label("", skin1);
        captchaError.setColor(Color.RED);

        captchaReload = new TextButton("Reload", skin1);
        captchaReload.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                generateCaptcha();
            }
        });

        savePassword = new TextButton("Save", skin1);

        changePasswordDialog = new Dialog("", skin1);
        changePasswordDialog.button("Cancel");
        changePasswordDialog.setColor(Color.WHITE);
        changePasswordDialog.getTitleLabel().setColor(Color.BLACK);
        changePasswordDialog.hide();
        changePasswordDialog.getContentTable().add(new Label("Old Password:", skin1));
        changePasswordDialog.getContentTable().add(oldPassword);
        changePasswordDialog.getContentTable().add(oldPasswordError).row();
        changePasswordDialog.getContentTable().add(new Label("New Password:", skin1));
        changePasswordDialog.getContentTable().add(newPassword);
        changePasswordDialog.getContentTable().add(newPasswordError).row();
        changePasswordDialog.getContentTable().add(captcha);
        changePasswordDialog.getContentTable().add(captchaField);
        changePasswordDialog.getContentTable().add(captchaReload).row();
        changePasswordDialog.getContentTable().add(captchaError).center().row();
        changePasswordDialog.getContentTable().add(savePassword).center();

        savePassword.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!("" + captchaNumber).equals(captchaField.getText())) {
                    captchaError.setText("incorrect captcha");
                    generateCaptcha();
                } else if (newPasswordError.getText().isEmpty()) {
                    captchaError.setText("");
                    String status = ProfileMenuController.checkChangePassword(user, oldPassword.getText());
                    oldPasswordError.setText(status);
                    if (status.isEmpty()) {
                        ProfileMenuController.changePassword(user, newPassword.getText());
                        changePasswordDialog.hide();
                        oldPassword.setText("");
                        newPassword.setText("");
                        oldPasswordError.setText("");
                        showSuccess("Password changed successfully");
                    } else generateCaptcha();
                }
            }
        });
    }

    private void generateCaptcha() {
        captchaNumber = CaptchaBuilder.captchaBuilder();
        if (captcha == null) captcha = new Image();
        captcha.setDrawable(new TextureRegionDrawable(
                new Texture(Gdx.files.internal("captcha/" + captchaNumber + ".png"))));
    }

    private void createBack() {
        backButton = new TextButton("Back", skin1);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainScreen(game, user));
            }
        });
    }

    private void createTable() {
        table = new Table(skin1);
        //table.setBackground(white.getDrawable());
        //table.setColor(Color.BLACK);
        table.setSize(600, 600);
        table.setPosition(uiStage.getWidth() / 2 - table.getWidth() / 2,
                uiStage.getHeight() / 2 - table.getHeight() / 2);
        table.add(avatar).colspan(2).row();

        table.add(selectAvatar).left();
        table.add(changeAvatar).right().row();
        table.add(username).left();
        table.add(changeUsername).right().row();
        table.add(nickname).left();
        table.add(changeNickname).right().row();
        table.add(email).left();
        table.add(changeEmail).right().row();
        table.add(slogan).left();
        table.add(removeSlogan).center();
        table.add(changeSlogan).right().row();
        table.add(changePassword).center().row();
        table.add(backButton).center();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        uiStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        table.setPosition(uiStage.getWidth() / 2 - table.getWidth() / 2,
                uiStage.getHeight() / 2 - table.getHeight() / 2);
        scoreBoardWindow.setHeight(uiStage.getHeight()-100);
        uiStage.draw();
    }
}
