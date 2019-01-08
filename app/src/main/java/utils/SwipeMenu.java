package utils;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;

import demo.yqh.dmdcspapp.R;

public class SwipeMenu {
    private static SwipeMenuCreator swipeMenuCreator;

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    public static SwipeMenuCreator CreateSwipeMenu(final Context context, final int background1, final int background2, final int image1, final int image2, final int textColor) {
        swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(com.yanzhenjie.recyclerview.swipe.SwipeMenu swipeLeftMenu, com.yanzhenjie.recyclerview.swipe.SwipeMenu swipeRightMenu, int viewType) {
                int width = context.getResources().getDimensionPixelSize(R.dimen.x70);
                // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
                // 2. 指定具体的高，比如80;
                // 3. WRAP_CONTENT，自身高度，不推荐;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;

                // 添加左侧的，如果不添加，则左侧不会出现菜单。
//                {
//                    SwipeMenuItem addItem = new SwipeMenuItem(context)
//                            .setBackground(R.drawable.selector_red)
//                            .setImage(R.drawable.ic_action_add)
//                            .setWidth(width)
//                            .setHeight(height);
//                    swipeLeftMenu.addMenuItem(addItem); // 添加菜单到左侧。
//
//                    SwipeMenuItem closeItem = new SwipeMenuItem(context)
//                            .setBackground(R.drawable.selector_red)
//                            .setImage(R.drawable.ic_action_add)
//                            .setWidth(width)
//                            .setHeight(height);
//                    swipeLeftMenu.addMenuItem(closeItem); // 添加菜单到左侧。
//                }

                // 添加右侧的，如果不添加，则右侧不会出现菜单。
                {
                    SwipeMenuItem Item1 = new SwipeMenuItem(context)
                            .setBackground(background1)
                            .setImage(image1)
                            .setText("")
                            .setTextColor(textColor)
                            .setWidth(width)
                            .setHeight(height);
                    swipeRightMenu.addMenuItem(Item1);// 添加菜单到右侧。

                    SwipeMenuItem Item2 = new SwipeMenuItem(context)
                            .setBackground(background2)
                            .setImage(image2)
                            .setText("")
                            .setTextColor(textColor)
                            .setWidth(width)
                            .setHeight(height);
                    swipeRightMenu.addMenuItem(Item2); // 添加菜单到右侧。
                }
            }
        };
        return swipeMenuCreator;
    }


    public static SwipeMenuCreator CreateSwipeMenu(final Context context, final Drawable background1, final Drawable background2, final Drawable image1, final Drawable image2, final int textColor) {
        swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(com.yanzhenjie.recyclerview.swipe.SwipeMenu swipeLeftMenu, com.yanzhenjie.recyclerview.swipe.SwipeMenu swipeRightMenu, int viewType) {
                int width = context.getResources().getDimensionPixelSize(R.dimen.x70);
                int height = ViewGroup.LayoutParams.MATCH_PARENT;

                {
                    SwipeMenuItem Item1 = new SwipeMenuItem(context)
                            .setBackground(background1)
                            .setImage(image1)
                            .setText("")
                            .setTextColor(textColor)
                            .setWidth(width)
                            .setHeight(height);
                    swipeRightMenu.addMenuItem(Item1);

                    SwipeMenuItem Item2 = new SwipeMenuItem(context)
                            .setBackground(background2)
                            .setImage(image2)
                            .setText("")
                            .setTextColor(textColor)
                            .setWidth(width)
                            .setHeight(height);
                    swipeRightMenu.addMenuItem(Item2);
                }
            }
        };
        return swipeMenuCreator;
    }
}
