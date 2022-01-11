package com.starfish.util;

import com.starfish.context.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * PinyinPlusTest
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-03-03
 */
public class PinyinUtilTest {

    @Test
    public void sortTest() {
        List<User> users = new ArrayList<>();

        User u1 = new User();
        u1.setUserId(1L);
        u1.setUserName("孙");
        users.add(u1);

        User u2 = new User();
        u2.setUserId(2L);
        u2.setUserName("马");
        users.add(u2);

        PinyinUtil.sort(users, User::getUserName);

        System.out.println(users);
    }

}
