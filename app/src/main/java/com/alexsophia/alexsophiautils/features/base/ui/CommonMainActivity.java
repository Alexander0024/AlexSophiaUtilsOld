package com.alexsophia.alexsophiautils.features.base.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alexsophia.alexsophiautils.R;
import com.alexsophia.alexsophiautils.share.adapters.CommonAdapter;
import com.alexsophia.alexsophiautils.share.adapters.ViewHolder;
import com.alexsophia.alexsophiautils.share.views.slidingmenu.SlidingMenu;
import com.alexsophia.alexsophiautils.share.views.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 此类为MainActivity的父类，只要在MainActivity中实现该实现的方法即可
 */
public abstract class CommonMainActivity extends BaseFragmentActivity {
    private String TAG = "CommonMainActivity";
    /**
     * 主界面相关控件
     */
    @Bind(R.id.tab1_iv_main)
    ImageView mTab1Img; // 第一个Tab的图标
    @Bind(R.id.tab2_iv_main)
    ImageView mTab2Img; // 第二个Tab的图标
    @Bind(R.id.tab3_iv_main)
    ImageView mTab3Img; // 第三个Tab的图标
    @Bind(R.id.tab1_tv_main)
    TextView mTab1Tv; // 第一个Tab的文字
    @Bind(R.id.tab2_tv_main)
    TextView mTab2Tv; // 第二个Tab的文字
    @Bind(R.id.tab3_tv_main)
    TextView mTab3Tv; // 第三个Tab的文字

    /**
     * 菜单相关控件
     */
    @Bind(R.id.sliding_menu)
    SlidingMenu mSlidingMenu; // 菜单
    ListView mListView; // 菜单列表
    RoundedImageView mRoundedImageView; // 用户头像
    TextView mNameTv; // 用户姓名

    protected List<Fragment> fragments; // 主界面三个fragment切换
    private List<MenuBean> datas; // 菜单列表数据
    private CommonAdapter<MenuBean> adapter; // 菜单列表的Adapter
    private FragmentManager mFragmentManager; // 碎片管理器
    private Fragment mContent; // 当前展示的fragment标识

    @Override
    protected int getContentViewRes() {
        return R.layout.main_activity;
    }

    @Override
    protected Activity getTarget() {
        return this;
    }

    /**
     * init
     */
    @Override
    protected void loadData() {
        /**
         * 初始化菜单
         */
        initMenu();
        /**
         * 初始化碎片及碎片管理器
         */
        fragments = new ArrayList<>();
        addFragments();
        mFragmentManager = getSupportFragmentManager();
        /**
         * 默认进入第一个页面（消息公告）
         */
        selectTab(0, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 初始化菜单
     */
    private void initMenu() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        final View menuLayout = inflater.inflate(R.layout.menu_layout, null);
        mListView = (ListView) menuLayout.findViewById(R.id.lv_menu);
        mRoundedImageView = (RoundedImageView) menuLayout.findViewById(R.id.iv_menu);
        mNameTv = (TextView) menuLayout.findViewById(R.id.tv_menu_name);
        /**
         * 初始化菜单项目
         */
        datas = new ArrayList<>();
        datas.add(new MenuBean(R.string.my_text, getIcon()));
        datas.add(new MenuBean(R.string.setting_text, getIcon()));
        datas.add(new MenuBean(R.string.about_text, getIcon()));
        datas.add(new MenuBean(R.string.check_update, getIcon(), getVersion()));
        datas.add(new MenuBean(R.string.exit_login_text, -1));
        /**
         * 初始化适配器
         */
        adapter = new CommonAdapter<MenuBean>(this, R.layout.menu_item, datas) {
            @Override
            public void covertView(final ViewHolder viewholder, MenuBean menuBean) {
                /**
                 * 设置菜单名称
                 */
                viewholder.setText(R.id.tv_name_menu_item, menuBean.getMenuName());
                /**
                 * 为-1时不展示右箭头（退出项使用）
                 */
                if (menuBean.getImgRes() != -1) {
                    viewholder.setImageViewResource(R.id.iv_menu_item, menuBean.getImgRes());
                }
                /**
                 * 包含第二参数版本信息时，刷新展示版本信息的辅助说明
                 * 目前v1.0版包含如下：
                 * 有新版本时显示：发现新版本
                 * 无新版本时显示：已是最新
                 */
                if (menuBean.getVersion() != null) {
                    viewholder.setText(R.id.tv_menu_version_item, menuBean.getVersion());
                    viewholder.setViewVisibility(R.id.tv_menu_version_item, View.VISIBLE);
                } else {
                    viewholder.setViewVisibility(R.id.tv_menu_version_item, View.GONE);
                }
                /**
                 * 设置每一项的点击事件
                 */
                viewholder.setClickListener(R.id.rl_menu_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeActivity(viewholder.getPos());
                    }
                });
            }
        };
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        /**
         * 初始化菜单中的家长姓名
         */
        mNameTv.setText(getName());

        /**
         * 初始化SlidingMenu属性
         */
        mSlidingMenu.setMode(SlidingMenu.LEFT);
        mSlidingMenu.setShadowDrawable(R.drawable.background_shadow);
        mSlidingMenu.setShadowWidth(50);
        mSlidingMenu.setMenu(menuLayout);
        mSlidingMenu.setBehindCanvasTransformer(new SlidingMenu.CanvasTransformer() {
            @Override
            public void transformCanvas(Canvas canvas, float percentOpen) {
                float scale = (float) (percentOpen * 0.3 + 0.7);
                canvas.scale(scale, scale, canvas.getWidth(), canvas.getHeight() / 2);
            }
        });
//        mSlidingMenu.setAboveCanvasTransformer(new SlidingMenu.CanvasTransformer() {
//            @Override
//            public void transformCanvas(Canvas canvas, float percentOpen) {
//                float scale = (float) ((1 - percentOpen) * 0.3 + 0.7);
//                canvas.scale(scale, scale, 0, canvas.getHeight() / 2);
//            }
//        });
    }

    /**
     * 添加fragment到Activity
     */
    protected void addFragments() {
    }

    /**
     * 菜单选择后跳转的Activity或者Fragment
     */
    private void changeActivity(int i) {
        switch (i) {
            case 0:
                toMyInfo();
                break;
            case 1:
                toSetting();
                break;
            case 2:
                toAbout();
                break;
            case 3:
                toCheckUpdate();
                break;
            case 4:
                toExit();
                break;
            default:
                break;
        }
    }

    /**
     * 点击选中后切换TAB，切换下方导航栏的字体及图片
     */
    private void selectTab(int pos, boolean init) {
        switch (pos) {
            case 0:
                mTab1Tv.setTextColor(getFocusTabColor());
                mTab2Tv.setTextColor(getNoFocusTabColor());
                mTab3Tv.setTextColor(getNoFocusTabColor());
                mTab1Img.setImageResource(getTab1FocusImgRes());
                mTab2Img.setImageResource(getTab2NoFocusImgRes());
                mTab3Img.setImageResource(getTab3NoFocusImgRes());
                break;
            case 1:
                mTab2Tv.setTextColor(getFocusTabColor());
                mTab1Tv.setTextColor(getNoFocusTabColor());
                mTab3Tv.setTextColor(getNoFocusTabColor());
                mTab2Img.setImageResource(getTab2FocusImgRes());
                mTab1Img.setImageResource(getTab1NoFocusImgRes());
                mTab3Img.setImageResource(getTab3NoFocusImgRes());
                break;
            case 2:
                mTab3Tv.setTextColor(getFocusTabColor());
                mTab2Tv.setTextColor(getNoFocusTabColor());
                mTab1Tv.setTextColor(getNoFocusTabColor());
                mTab3Img.setImageResource(getTab3FocusImgRes());
                mTab2Img.setImageResource(getTab2NoFocusImgRes());
                mTab1Img.setImageResource(getTab1NoFocusImgRes());
                break;
            default:
                break;
        }
        /**
         * 切换碎片
         */
        changeFragment(pos);
    }

    /**
     * 切换碎片
     *
     * @param position 点击的Position
     */
    private void changeFragment(int position) {
        if (fragments != null && !fragments.isEmpty()) {
            /**
             * 获取点击的碎片
             */
            Fragment fragment = fragments.get(position);
            /**
             * 判断划动动画是从左进入还是从右进入
             * 如果点击的是第一个页面，则从左侧划入
             * 如果点击的是第三个页面，则从右侧划入
             * 如果点击的是中间页面，当前在第三个页面，则从左侧划入
             */
            boolean isSlideFromLeft = 0 == position
                    || 2 != position && mContent.equals(fragments.get(2));
            switchContent(fragment, isSlideFromLeft);
        }
    }

    /**
     * 执行切换Fragment的操作
     *
     * @param to          需要切换至的fragment
     * @param isSlideLeft 切换动画是否为从左侧划入
     */
    private void switchContent(Fragment to, boolean isSlideLeft) {
        if (mContent != to) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            if (isSlideLeft) {
                /**
                 * →→→→→→→→→→
                 */
                transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
            } else {
                /**
                 * ←←←←←←←←←←
                 */
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            /**
             * 执行切换
             */
            if (null == mContent) {
                /**
                 * 如果content为空，即初始化，直接添加至显示即可。
                 */
                transaction.add(R.id.content_main, to).commit();
            } else if (!to.isAdded()) {
                /**
                 * 如果需要进入的fragment没有被Add过
                 * 隐藏当前的fragment，add下一个到Activity中。
                 */
                transaction.hide(mContent).add(R.id.content_main, to).commit();
            } else {
                /**
                 * 如果已经被Add过
                 * 隐藏当前的fragment，直接显示到需要去的fragment。
                 */
                transaction.hide(mContent).show(to).commit();
            }
            mContent = to;
        }
    }

    /**
     * 未点击时tab文字的颜色
     */
    private int getFocusTabColor() {
        return getResources().getColor(R.color.main_tab_text_color_focused);
    }

    /**
     * 点击时tab文字的颜色
     */
    private int getNoFocusTabColor() {
        return getResources().getColor(R.color.main_tab_text_color);
    }

    @OnClick({ R.id.tab1_main, R.id.tab2_main, R.id.tab3_main})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.tab1_main:
                selectTab(0, false);
                break;
            case R.id.tab2_main:
                selectTab(1, false);
                break;
            case R.id.tab3_main:
                selectTab(2, false);
                break;
        }
    }

    /**
     * 切换菜单的打开状态
     */
    protected void toggleMenuPanel() {
        mSlidingMenu.toggle();
    }

    /**
     * 菜单是否打开的判断标志
     *
     * @return 菜单是否打开
     */
    protected boolean isMenuAlreadyOpened() {
        return mSlidingMenu.isMenuShowing();
    }

    /**
     * tab1不被点击时的背景res
     */
    protected abstract int getTab1NoFocusImgRes();

    /**
     * tab1被点击时的背景res
     */
    protected abstract int getTab1FocusImgRes();

    /**
     * tab2不被点击时的背景res
     */
    protected abstract int getTab2NoFocusImgRes();

    /**
     * tab2被点击时的背景res
     */
    protected abstract int getTab2FocusImgRes();

    /**
     * tab3不被点击时的背景res
     */
    protected abstract int getTab3NoFocusImgRes();

    /**
     * tab3被点击时的背景res
     */
    protected abstract int getTab3FocusImgRes();

    /**
     * 获取名字
     */
    protected abstract String getName();

    /**
     * 获取菜单右边的图标
     */
    protected abstract int getIcon();

    /**
     * 获取版本信息
     */
    protected abstract String getVersion();

    class MenuBean {
        private int menuName;
        private int imgRes;
        private String version;

        public MenuBean(int menuName, int imgRes) {
            this.menuName = menuName;
            this.imgRes = imgRes;
        }

        public MenuBean(int menuName, int imgRes, String version) {
            this.menuName = menuName;
            this.imgRes = imgRes;
            this.version = version;
        }

        public String getVersion() {
            return version;
        }

        /**
         * 添加更新版本信息的setter
         *
         * @param value 版本信息
         */
        public void setVersion(String value) {
            version = value;
        }

        public int getMenuName() {
            return menuName;
        }

        public int getImgRes() {
            return imgRes;
        }
    }

    /**
     * 退出登录
     */
    protected abstract void toExit();

    /**
     * 检查更新
     */
    protected abstract void toCheckUpdate();

    /**
     * 跳转至关于界面
     */
    protected abstract void toAbout();

    /**
     * 跳转至设置界面
     */
    protected abstract void toSetting();

    /**
     * 跳转至我的资料界面
     */
    protected abstract void toMyInfo();
}
