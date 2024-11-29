package test;

import cn.myphoenix.BallSports;
import org.junit.Test;

/**
 * @author: larry_lwh
 * @date: 2024-11-29 21:47
 * @description: 球类运动测试
 **/
public class BallSportsTest {
    @Test
    public void testPlayBasketball() {
        BallSports ballSports = new BallSports();
        ballSports.playBasketBall();
        ballSports.playFootBall();
    }
}
