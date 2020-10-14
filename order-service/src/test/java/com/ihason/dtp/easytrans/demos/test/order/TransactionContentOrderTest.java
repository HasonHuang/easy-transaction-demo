package com.ihason.dtp.easytrans.demos.test.order;

import com.ihason.dtp.easytrans.demos.order.service.saga.request.UpdateOrderStatusRequest;
import com.yiqiniu.easytrans.context.LogProcessContext;
import com.yiqiniu.easytrans.log.LogCache;
import com.yiqiniu.easytrans.log.TransactionLogWritter;
import com.yiqiniu.easytrans.log.vo.Content;
import com.yiqiniu.easytrans.log.vo.LogCollection;
import com.yiqiniu.easytrans.log.vo.saga.PreSagaTccCallContent;
import com.yiqiniu.easytrans.protocol.TransactionId;
import com.yiqiniu.easytrans.protocol.saga.SagaTccMethodRequest;
import com.yiqiniu.easytrans.util.ReflectUtil;
import io.seata.common.util.ReflectionUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 事务内容排序的测试用例
 *
 * @author Hason
 */
@RunWith(Parameterized.class)
public class TransactionContentOrderTest {

    private LogProcessContext context = mock(LogProcessContext.class);

    private Executor executor;

    @Before
    public void setup() {
        executor = Executors.newFixedThreadPool(10);
    }

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[1][0]; // repeat count which you want
    }

    @Test
    public void genOrder() throws InterruptedException, NoSuchFieldException, IllegalAccessException {
//        LogCollection collection = mock(LogCollection.class);
        LogCollection collection = new LogCollection("appId", "biz", 100, new ArrayList<>(), new Date());
        when(context.getLogCollection()).thenReturn(collection);
        when(context.getWriter()).thenReturn(mock(TransactionLogWritter.class));
        when(context.getTransactionId()).thenReturn(new TransactionId());
//        when(collection.getOrderedContents()).thenReturn(new ArrayList<>());
        LogCache cache = new LogCache(context);

        int size = 10;
        CountDownLatch latch = new CountDownLatch(size);

        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            // 每个任务缓存 100 个内容
            executor.execute(new Task(i, cache, latch));
        }
        
        latch.await();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + "ms");

        // 验证内容顺序
        cache.flush(true);
        List<Content> orderedContents = context.getLogCollection().getOrderedContents();
        for (int i = 0; i < orderedContents.size(); i++) {
            PreSagaTccCallContent content = (PreSagaTccCallContent) orderedContents.get(i);
            TaskRequest request = (TaskRequest) content.getParams();
            System.out.printf("内容 ID [%d] 线程 ID [%d] 任务 ID [%d]%n", content.getcId(), request.getTaskId(), request.getOrderId());
            if (content.getcId() != (i + 1)) {
                System.err.printf("发现 ID 生成错误，预期是 [%d] 实际是 [%d]%n", i + 1, content.getcId());
            }
        }
    }

    public class Task implements Runnable {
        private Integer taskId;
        private LogCache cache;
        private CountDownLatch latch;

        public Task(Integer taskId, LogCache cache, CountDownLatch latch) {
            this.taskId = taskId;
            this.cache = cache;
            this.latch = latch;
        }

        @SneakyThrows
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                PreSagaTccCallContent content = new PreSagaTccCallContent();
                content.setParams(new TaskRequest(i, taskId));
                cache.cacheLog(content);
                if (i % 3 == 0) {
                    cache.flush(false);
                }
            }
            TimeUnit.MILLISECONDS.sleep(500);
            if (latch != null) {
                latch.countDown();
            }
        }
    }

    @Data
    @AllArgsConstructor
    public static class TaskRequest implements SagaTccMethodRequest {
        private Integer orderId;
        private Integer taskId;
    }

}
