package org.softwarevax.framework.test;

import com.alibaba.fastjson.JSON;
import org.softwarevax.framework.context.ApplicationContext;
import org.softwarevax.framework.context.SpringApplicationRunner;
import org.softwarevax.framework.core.annotations.SpringApplicationVax;
import org.softwarevax.framework.test.entity.DeployTask;
import org.softwarevax.framework.test.service.impl.DeployTaskServiceImpl;

import java.util.List;


@SpringApplicationVax(packages = "org.softwarevax.framework.test")
public class TestApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplicationRunner.run(TestApplication.class);
        DeployTaskServiceImpl testService = context.getBean(DeployTaskServiceImpl.class);
        List<DeployTask> all = testService.findAll();
        System.out.println(JSON.toJSONString(all));
    }
}



/** binlog

 BinaryLogClient client = new BinaryLogClient("localhost", 3306, "root", "123456");
 EventDeserializer eventDeserializer = new EventDeserializer();
 //时间反序列化的格式
 //        eventDeserializer.setCompatibilityMode(
 //                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
 //                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
 //        );
 client.setEventDeserializer(eventDeserializer);

 client.registerEventListener(event -> {
 EventHeader header = event.getHeader();
 EventType eventType = header.getEventType();
 if (EventType.isWrite(eventType)) {
 //获取事件体
 WriteRowsEventData data = event.getData();
 System.out.println(data);
 } else if (EventType.isUpdate(eventType)) {
 UpdateRowsEventData data = event.getData();
 System.out.println(data);
 } else if (EventType.isDelete(eventType)) {
 DeleteRowsEventData data = event.getData();
 System.out.println(data);
 }
 });
 client.connect();
 */

/**
 // spring
 //ApplicationContext context = SpringApplicationRunner.run(TestApplication.class);
 //DeployTaskServiceImpl testService = context.getBean(DeployTaskServiceImpl.class);
 //testService.findAll();
 */