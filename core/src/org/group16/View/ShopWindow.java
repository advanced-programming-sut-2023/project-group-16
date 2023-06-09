package org.group16.View;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.group16.Model.Game;
import org.group16.Model.Resources.BasicResource;
import org.group16.Model.Resources.Food;
import org.group16.Model.Resources.Resource;
import org.group16.Model.Resources.Weaponry;


import java.util.ArrayList;
import java.util.HashMap;

public class ShopWindow extends Window {

    Image soilBackground, grayBackGround;
    public Skin skin;
    final int numberOfPics = 5, xUp = 60, yUp = 60, xDown = 50, yDown = 50;
    int startPic = 0;
    TypeOfGood curTypeOfGood = TypeOfGood.FOOD;
    HashMap<TypeOfGood, ArrayList<Resource>> typeOfGoodArrayListHashMap = new HashMap<>();

    {
        typeOfGoodArrayListHashMap.put(TypeOfGood.BASIC, new ArrayList<>());
        for (Resource resource : BasicResource.values()) {
            if (resource.getPrice() < Integer.MAX_VALUE)
                typeOfGoodArrayListHashMap.get(TypeOfGood.BASIC).add(resource);
        }
        typeOfGoodArrayListHashMap.put(TypeOfGood.WEAPONRY, new ArrayList<>());
        for (Resource resource : Weaponry.values()) {
            if (resource.getPrice() < Integer.MAX_VALUE)
                typeOfGoodArrayListHashMap.get(TypeOfGood.WEAPONRY).add(resource);
        }
        typeOfGoodArrayListHashMap.put(TypeOfGood.FOOD, new ArrayList<>());
        for (Resource resource : Food.values()) {
            if (resource.getPrice() < Integer.MAX_VALUE)
                typeOfGoodArrayListHashMap.get(TypeOfGood.FOOD).add(resource);
        }

    }

    Label status;

    ImageButtonElement[] imageButtonElements = new ImageButtonElement[numberOfPics];


    public ImageButton backImageButton, nextImageButton, foodButton, weaponryButton, basicButton , back;

    BuyingWindow buyingWindow;

    Game game  ;
    testingGameScreen gameScreen ;

    public ShopWindow(Skin skin, Game game ,testingGameScreen gameScreen) {
        super("", skin);

        this.game = game ;
        this.gameScreen = gameScreen ;

        this.skin = skin;
        status = new Label("", skin);
        status.setColor(Color.BLACK);
        soilBackground = new Image(new Texture(Gdx.files.internal("backgrounds/soilBackground.jpg")));
        grayBackGround = new Image(new Texture(Gdx.files.internal("backgrounds/gray.png")));
        this.setBackground(soilBackground.getDrawable());

        backImageButton = new ImageButton(skin);
        makeButtons(backImageButton, "ButtonImages/BackButton.png", "ButtonImages/BackButton.png");
        nextImageButton = new ImageButton(skin);
        makeButtons(nextImageButton, "ButtonImages/NextButton.png", "ButtonImages/NextButton.png");
        foodButton = new ImageButton(skin);
        makeButtons(foodButton, "ButtonImages/FoodButton.png", "ButtonImages/FoodButton.png");
        weaponryButton = new ImageButton(skin);
        makeButtons(weaponryButton, "ButtonImages/WeaponryButton.png", "ButtonImages/WeaponryButton.png");
        basicButton = new ImageButton(skin);
        makeButtons(basicButton, "ButtonImages/BasicButton.png", "ButtonImages/BasicButton.png");

        back = new ImageButton(skin);
        ImageButton.ImageButtonStyle imageStyle = new ImageButton.ImageButtonStyle();
        imageStyle.imageUp = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("ButtonImages/BackButton.png").path(), 30, 30));
        imageStyle.imageDown = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("ButtonImages/BackButton.png").path(), 27, 27));
        back.setStyle(imageStyle);


        this.add(status).row();

        this.add(backImageButton).pad(0, 0, 0, 20);
        backImageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startPic -= numberOfPics;
                if (startPic < 0)
                    startPic = 0;
                remake();
            }
        });

        for (int i = 0; i < numberOfPics; i++) {
            imageButtonElements[i] = new ImageButtonElement();
            this.add(imageButtonElements[i].imageButton);
        }
        remake();

        this.add(nextImageButton).pad(0, 20, 0, 0).row();
        this.row();
        nextImageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startPic += numberOfPics;
                remake();
            }
        });
        this.add(foodButton).pad(0, 0, 0, 5);
        foodButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startPic = 0;
                curTypeOfGood = TypeOfGood.FOOD;
                remake();
            }
        });

        this.add(weaponryButton).pad(0, 0, 0, 5);
        weaponryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startPic = 0;
                curTypeOfGood = TypeOfGood.WEAPONRY;
                remake();
            }
        });

        this.add(basicButton);
        basicButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startPic = 0;
                curTypeOfGood = TypeOfGood.BASIC;
                remake();
            }
        });


        this.row() ;

        this.add(back) ;


        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.setCurrentRunningWindow(gameScreen.buildingWindow);
            }
        }) ;

    }

    public void makeButtons(ImageButton imageButton, String up, String down) {
        try {
            ImageButton.ImageButtonStyle imageStyle = new ImageButton.ImageButtonStyle();
            imageStyle.imageUp = new TextureRegionDrawable(picChange.changer(Gdx.files.internal(up).path(), xUp, yUp));
            imageStyle.imageDown = new TextureRegionDrawable(picChange.changer(Gdx.files.internal(down).path(), xDown, yDown));
            imageButton.setStyle(imageStyle);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public class ImageButtonElement {

        ImageButton imageButton;
        ImageButton.ImageButtonStyle imageButtonStyle;
        String name;
        Resource resource;

        public ImageButtonElement() {
            imageButtonStyle = new ImageButton.ImageButtonStyle();
            imageButtonStyle.imageUp = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("backgrounds/soilBackground.jpg").path(), xUp, yUp));
            imageButtonStyle.imageDown = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("backgrounds/soilBackground.jpg").path(), xDown, yDown));
            imageButton = new ImageButton(imageButtonStyle);

            name = "null";

            imageButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    status.setText(name);
                    try {
                        gameScreen.buyingWindow.remake(resource);
                        gameScreen.setCurrentRunningWindow(gameScreen.buyingWindow) ;

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            });
        }

        public void setNewImage(Resource resource, TypeOfGood typeOfGood) {
            this.resource = resource;
            if (typeOfGood.equals(TypeOfGood.NULL)) {
                imageButtonStyle.imageUp = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("backgrounds/soilBackground.jpg").path(), xUp, yUp));
                imageButtonStyle.imageDown = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("backgrounds/soilBackground.jpg").path(), xDown, yDown));
                name = "null";
            } else {
                this.name = resource.GetName();
                try {
                    imageButtonStyle.imageUp = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("MenuPictures/Resources/" + typeOfGood.name() + "/" + name + ".png").path(), xUp, yUp));
                    imageButtonStyle.imageDown = new TextureRegionDrawable(picChange.changer(Gdx.files.internal("MenuPictures/Resources/" + typeOfGood.name() + "/" + name + ".png").path(), xDown, yDown));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void remake() {
        int i2 = numberOfPics;
        if (startPic > typeOfGoodArrayListHashMap.get(curTypeOfGood).size())
            startPic -= numberOfPics;
        for (int i = startPic; i < startPic + numberOfPics && i < typeOfGoodArrayListHashMap.get(curTypeOfGood).size(); i++) {
            imageButtonElements[i - startPic].setNewImage(typeOfGoodArrayListHashMap.get(curTypeOfGood).get(i), curTypeOfGood);
            i2 = i - startPic;
        }
        i2++;
        for (; i2 < numberOfPics; i2++) {
            imageButtonElements[i2].setNewImage(null, TypeOfGood.NULL);
        }
    }

    public enum TypeOfGood {
        FOOD,
        WEAPONRY,
        BASIC,
        NULL
    }


}
