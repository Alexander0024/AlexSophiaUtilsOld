package com.alexsophia.alexsophiautils.utils;

/**
 * 常量信息存储
 * Created by liuweiping on 2016-3-8.
 */
public class Constant {
    /**
     * 服务器路径信息，用于ApiManager初始化
     */
    public static final String API_BASE_URL = "http://sdk.ilr1000.com/";

    /**
     * 判断用户是否需要登录
     * <p/>
     * 需求对此功能的描述为：要求用户每次都登录
     * <p/>
     * TODO: 暂留开关供开发调试使用，后期改为true即可
     */
    public static final boolean NEED_LOGIN = true;

    /**
     * 用户许可协议的地址
     */
    public static final String LICENCE_LOCATION = "file:///android_asset/licence.html";

    public static final String PERCENT = "%";

    /**
     * 后门账户
     */
    public static final String BACKDOOR_NAME = "112212";
    /**
     * 后门密码
     */
    public static final String BACKDOOR_PSW = "111111";


    /**
     * Activity间参数传递
     */
    public static class ACTIVITY_PARAMS {
        /**
         * 传递E-mail信息
         */
        public static final String EMAIL = "mail";

        /**
         * 传递Phone信息
         */
        public static final String PHONE = "phone";

        /**
         * 传递Account信息
         */
        public static final String ACCOUNT = "account";

        /**
         * 传递Shadow_code信息
         */
        public static final String SHADOW_CODE = "shadow_code";

        /**
         * 用户的UID
         */
        public static final String UID = "uid";

        /**
         * 传递的ID信息
         */
        public static final String ID = "id";

        /**
         * 传递的名称信息
         */
        public static final String NAME = "name";

        /**
         * 用于菜单打开的传参
         */
        public static final int REQUEST = 0;

        /**
         * 返回值
         */
        public static final int RESULT_CODE = 77;

        /**
         * 消息公告的Message ID，用于回复消息
         */
        public static final String MESSAGE_ID = "message_id";

        /**
         * 消息公告的Content ID，用于获取详情
         */
        public static final String CONTENT_ID = "content_id";
    }


    /**
     * Android Platform
     */
    public static final int PLATFORM = 5;

    /**
     * 版本号
     */
    public static final String VERSION = "3.1";

    /**
     * Android客户端类型信息
     */
    public static final int CLIENT_TYPE = 33;

    /**
     * 标记初始化页面的等待时间
     */
    public static final long On_BOARDING_DELAY = 1000;

    /**
     * 请求获取权限的requestCode
     */
    public static final int REQUEST_PERMISSION = 7;

    /**
     * 每个列表一次请求的数据量
     */
    public static final int COUNT = 20;
    /**
     * 作业
     */
    public static final int HOMEWORK = 1;
    /**
     * 预习
     */
    public static final int PREVIEW = 2;
    /**
     * 未读
     */
    public static final int UNREAD = 0;
    /**
     * 已读
     */
    public static final int READED = 1;
    /**
     * 作业编号
     */
    public static final String HOMEWORKID = "homeworkId";
    /**
     * 作业类型
     */
    public static final String HOMEWORKTYPE = "homeworkType";
    /**
     * 考试ID
     */
    public static final String ID = "id";
    /**
     * 网站信息
     */
    public static final String WEBSITE = "website";
    /**
     * 年级编号
     */
    public static final String GRADECODE = "gradeCode";
    /**
     * 类型
     */
    public static final String TYPE = "type";
    /**
     * 题目数据
     */
    public static final String QUESTION = "question";
    /**
     * 属于模块
     */
    public static final String LEARNTYPE = "learnType";
    /**
     * 题目位置
     */
    public static final String SUBNUM = "subnum";
    /**
     * 所有题数目
     */
    public static final String SIZE = "size";
    /**
     * 内容
     */
    public static final String CONTENT = "content";
    /**
     * 解析类型
     */
    public static final String ISALL = "isall";
    /**
     * 附件
     */
    public static final String ATTACH = "attach";
    /**
     * 科目名称
     */
    public static final String SUBJECTNAME = "subjectName";

    /**
     * 线上考试0
     */
    public static final int ONLINEEXAM = 0;
    /**
     * 线下考试1
     */
    public static final int OFFLINEEXAM = 1;
    /**
     * 练习
     */
    public static final int PRACTICE = 1;
    /**
     * 考试
     */
    public static final int EXAM = 2;
    /**
     * 错题本
     */
    public static final int ERRORBOOK = 3;
    /**
     * 未批阅
     */
    public static final int UNMARK = 0;
    /**
     * 对
     */
    public static final int RIGHT = 1;
    /**
     * 错
     */
    public static final int WRONG = 2;
    /**
     * 半对
     */
    public static final int HALTRIGHT = 3;
    // 分割符
    public static final String SEPANSWER = "!@#";

    /**
     * 学习任务类型
     */
    public class LEARN_TYPE{
        /**
         * 练习和测试
         */
        public static final int TYPE_PRACTICE = 1;
        /**
         * 线上考试
         */
        public static final int TYPE_ONLINE = 2;
        /**
         * 线下考试
         */
        public static final int TYPE_OFFLINE = 3;
        /**
         * 作业和预习
         */
        public static final int TYPE_HOMEWORK = 4;
        /**
         * 错题本
         */
        public static final int TYPE_ERROR = 5;
    }
    /**
     * 学习任务状态
     */
    public class LEARN_STATE {
        /**
         * 未提交
         */
        public static final int UNSUBMITTED = 0;
        /**
         * 已提交
         */
        public static final int SUBMITTED = 1;
        /**
         * 已批改
         */
        public static final int CORRECTED = 2;
        /**
         * 已过期
         */
        public static final int EXPIRED = 3;


    }
    /**
     * 错题本删选类型
     */
    public class ERROR_TYPE{
        /**
         * 本日
         */
        public static final int TODAY = 1;
        /**
         * 本周
         */
        public static final int WEEK = 2;
        /**
         * 本月
         */
        public static final int MONTH = 3;
        /**
         * 本学期
         */
        public static final int SEMESTER = 4;
    }

    /**
     * 用户上传头像相关常量
     */
    public class UPLOAD_AVATAR {
        /**
         * 相机拍照时存储的头像文件名称
         */
        public final static String IMAGE_FILE_NAME = "avatarImage.jpg";
        /**
         * 用户裁剪完成准备上传的头像文件名称
         */
        public final static String IMAGE_FOR_UPLOAD = "avatarUpload.jpg";
        /**
         * 相册选图标记
         */
        public final static int REQUEST_CODE_PICK = 0;
        /**
         * 相机拍照标记
         */
        public final static int REQUEST_CODE_TAKE = 1;
        /**
         * 图片裁切标记
         */
        public final static int REQUEST_CODE_CUTTING = 2;
    }

    /**
     * 发送短信验证码的接口
     */
    public class SMS_AUTH_TYPE {
        /**
         * 忘记密码的短信验证码
         */
        public static final int FORGOT_PASSWORD = 0;
        /**
         * 重置密码的短信验证码
         */
        public static final int RESET_PASSWORD = 1;
        /**
         * 绑定手机的短信验证码
         */
        public static final int BIND_MOBILE = 2;
        /**
         * 解绑手机的短信验证码
         */
        public static final int UNBIND_MOBILE = 3;
    }

    /**
     * 消息公告详情的分类，目前家长端系统仅使用到了收件箱
     */
    public class MESSAGE_STATE {
        /**
         * 收件箱
         */
        public static final String IN_BOX = "in_box";
        /**
         * 发件箱：未使用
         */
        public static final String OUT_BOX = "out_box";
        /**
         * 草稿箱：未使用
         */
        public static final String DRAFT_BOX = "draft_box";
    }

    /**
     * 标示在发送消息和联系人详情页面间传递收件人列表的参数
     */
    public static final int CHOOSE_CONTACT_PARAM = 789;

    /**
     * 消息公告的类型
     */
    public class MESSAGE_TYPE {
        /**
         * 消息
         */
        public static final int MESSAGE = 4;
        /**
         * 班级公告
         */
        public static final int CLASS_NEWS = 0;
        /**
         * 系统公告
         */
        public static final int SYSTEM_NEWS = 5;
    }

    /**
     * 用户列表分类
     */
    public enum CONTACT_TYPE {
        TEACHER, STUDENT, PARENT
    }

    /**
     * 载入中的状态
     */
    public enum LOADING_TYPE {
        SHOWN, LOADING, EMPTY
    }

    /**
     * 科目分类
     */
    public class SUBJECT {
        public static final String CHINESE = "语文";
        public static final String MATH = "数学";
        public static final String ENGLISH = "英语";
        public static final String IDEOLOGY = "思品";
        public static final String SCIENCE = "科学";
        public static final String HISTORY = "历史";
        public static final String GEOGRAPHY = "地理";
        public static final String POLITICS = "政治";
        public static final String BIOLOGY = "生物";
        public static final String PHYSICS = "物理";
        public static final String CHEMISTRY = "化学";
        public static final String SPORTS = "体育";
        public static final String INFORMATION = "信息技术";
        public static final String MUSIC = "音乐";
        public static final String ART = "美术";
    }
    /**
     * 答题题型 questionType 截前三位对应151 单选 152多选 153 填空题 154 判断题 155 简答题/写
     作题 156 阅读理解
     */
    public class QuestionType {
        /**
         * 单选
         */
        public static final int SINGLECHOICE = 1;
        /**
         * 多选
         */
        public static final int MULTISELECT = 2;
        /**
         * 填空题
         */
        public static final int COMPLETION = 3;
        /**
         * 判断题Judgment question
         */
        public static final int JUDGMENT = 4;
        /**
         * 简答题/写作题
         */
        public static final int SHORTANSWER = 5;
        /**
         * 阅读理解
         */
        public static final int READING = 6;

    }
    public class LEARN_NAME{
        /**
         * 解析title
         */
        public static final String TITLE_NAME = "titleName";
        /**
         * 作业题
         */
        public static final String HOMEWORK = "作业题";
        /**
         * 预习题
         */
        public static final String PREVIEW = "预习题";
        /**
         * 考试题
         */
        public static final String EXAM = "考试题";
        /**
         * 练习题
         */
        public static final String PRACTICE = "练习题";
        /**
         * 测试题
         */
        public static final String TEXT = "测试题";
        /**
         * 全部错题
         */
        public static final String ERROR = "全部错题";
    }
}