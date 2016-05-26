package com.alexsophia.alexsophiautils.features.user.repository;

import com.alexsophia.alexsophiautils.features.base.repository.ErrorBean;
import com.google.gson.annotations.SerializedName;

/**
 * 登录接口
 * Created by liuweiping on 2016-3-2.
 */
public class LoginBean extends ErrorBean {
    @SerializedName("token")
    private String token;
    @SerializedName("domain")
    private String domain;
    @SerializedName("user")
    private UserEntity user;

    public void setToken(String token) {
        this.token = token;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public String getDomain() {
        return domain;
    }

    public UserEntity getUser() {
        return user;
    }

    public static class UserEntity {
        @SerializedName("id")
        private long id;
        @SerializedName("account")
        private String account;
        @SerializedName("realname")
        private String realName;
        @SerializedName("identity_type")
        private int identityType;
        @SerializedName("email")
        private String email;
        @SerializedName("mobile_phone")
        private String mobilePhone;
        @SerializedName("status")
        private int status;
        @SerializedName("card_type")
        private int cardType;
        @SerializedName("avatar")
        private String avatar;
        @SerializedName("create_at")
        private long createAt;
        @SerializedName("school")
        private SchoolEntity school;
        @SerializedName("student")
        private StudentEntity student;

        public StudentEntity getStudent() {
            return student;
        }

        public void setStudent(StudentEntity student) {
            this.student = student;
        }

        public void setId(long id) {
            this.id = id;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public void setIdentityType(int identityType) {
            this.identityType = identityType;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setCardType(int cardType) {
            this.cardType = cardType;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setCreateAt(long createAt) {
            this.createAt = createAt;
        }

        public void setSchool(SchoolEntity school) {
            this.school = school;
        }

        public long getId() {
            return id;
        }

        public String getAccount() {
            return account;
        }

        public String getRealName() {
            return realName;
        }

        public int getIdentityType() {
            return identityType;
        }

        public String getEmail() {
            return email;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public int getStatus() {
            return status;
        }

        public int getCardType() {
            return cardType;
        }

        public String getAvatar() {
            return avatar;
        }

        public long getCreateAt() {
            return createAt;
        }

        public SchoolEntity getSchool() {
            return school;
        }

        public static class SchoolEntity {
            @SerializedName("id")
            private long id;
            @SerializedName("school_name")
            private String schoolName;

            public void setId(long id) {
                this.id = id;
            }

            public void setSchoolName(String schoolName) {
                this.schoolName = schoolName;
            }

            public long getId() {
                return id;
            }

            public String getSchoolName() {
                return schoolName;
            }
        }

        public static class StudentEntity {
            @SerializedName("user_id")
            private long userId;
            @SerializedName("realname")
            private String realName;
            @SerializedName("avatar")
            private String avatar;
            @SerializedName("class_code")
            private long classCode;
            @SerializedName("grade_code")
            private long gradeCode;
            @SerializedName("class_name")
            private String className;
            @SerializedName("grade_name")
            private String gradeName;

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public long getClassCode() {
                return classCode;
            }

            public void setClassCode(long classCode) {
                this.classCode = classCode;
            }

            public long getGradeCode() {
                return gradeCode;
            }

            public void setGradeCode(long gradeCode) {
                this.gradeCode = gradeCode;
            }

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }

            public String getGradeName() {
                return gradeName;
            }

            public void setGradeName(String gradeName) {
                this.gradeName = gradeName;
            }
        }
    }
}
