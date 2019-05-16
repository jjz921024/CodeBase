package cmb.limiter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jun on 2018/8/6.
 */
public class FunnelRateLimiter {

    static class Funnel {
        int capacity;  // 漏斗容量
        float leakingRate; //漏水速率
        int leftQuota;    //漏斗剩余空间
        long leakingTs;   //上次计算漏水的时间

        public Funnel(int capacity, float leakingRate) {
            this.capacity = capacity;
            this.leakingRate = leakingRate;
            this.leftQuota = capacity;
            this.leakingTs = System.currentTimeMillis();
        }

        // 每次灌水前计算剩余空间，与流速和时间有关
        void makeSpace() {
            long nowTs = System.currentTimeMillis();
            long deltaTs = nowTs - leakingTs;
            int deltaQuota = (int) (deltaTs * leakingRate);
            if (deltaQuota < 0) { // 间隔时间太长，整数数字过大溢出
                this.leftQuota = capacity;
                this.leakingTs = nowTs;
                return;
            }
            if (deltaQuota < 1) { // 腾出空间太小，最小单位是1
                return;
            }
            this.leftQuota += deltaQuota;
            this.leakingTs = nowTs;
            if (this.leftQuota > this.capacity) {
                this.leftQuota = this.capacity;
            }
        }

        // 灌水
        boolean watering(int quota) {
            makeSpace();
            // 剩余空间大于所需空间quota
            if (this.leftQuota >= quota) {
                this.leftQuota -= quota;
                return true;
            }
            return false;
        }
    }

    // 所有限流漏斗
    private Map<String, Funnel> funnels = new HashMap<>();

    public boolean isActionAllowed(String userId, String actionKey, int capacity, float leakingRate) {
        String key = String.format("%s:%s", userId, actionKey);
        Funnel funnel = funnels.get(key);
        if (funnel == null) {
            funnel = new Funnel(capacity, leakingRate);
            funnels.put(key, funnel);
        }
        // 设置需要1个quota即可放行
        return funnel.watering(1);
    }
}
