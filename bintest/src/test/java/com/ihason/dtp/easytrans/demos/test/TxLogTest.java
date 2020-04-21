package com.ihason.dtp.easytrans.demos.test;

import com.ihason.dtp.easytrans.demos.*;
import com.ihason.dtp.easytrans.demos.serializer.JdkSerializationSerializer;
import com.ihason.dtp.easytrans.demos.serializer.KryoTransactionSerializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TxLogTest {

    @Autowired
    private TxLogRepository repository;

    @Commit
    @Test
    public void create() {
        TxLog log = new TxLog();
        log.setName("adw");
        repository.save(log);
        assertThat(log.getId()).isNotNull();
    }

    @Commit
    @Test
    public void createBigObject() throws NoSuchMethodException {
        TxLog log = new TxLog();
        log.setName("big big bang");
        BigObjectNoSerializable noSerializable = BigObjectNoSerializable.ofAll();
        log.setContent(new BigObject(1L, "I am BigObject", noSerializable));
        repository.save(log);
    }

    @Test
    public void getBigObject() {
        TxLog log = repository.findOne(5L);
        assertThat(log.getContent()).isNotNull();
        System.out.println(log.getContent());
    }

    @Test
    public void ser() throws NoSuchMethodException {
        TxLog log = new TxLog();
        log.setName("big big bang");
        BigObjectNoSerializable noSerializable = BigObjectNoSerializable.ofAll();
        log.setContent(new BigObject(1L, "I am BigObject", noSerializable));
        System.out.println(new JdkSerializationSerializer<>().serialize(log.getContent()).length);
        System.out.println(new KryoTransactionSerializer<>().serialize(log.getContent()).length);
    }

}
