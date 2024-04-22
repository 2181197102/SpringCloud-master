package com.springboot.cloud.nsclcservice.nsclc.config;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.context.annotation.Configuration;

/**
 * Description: TODO
 *
 * @author: ykn
 * @date: 2024年04月06日 6:03 PM
 **/
@Configuration
public class ThriftConfig {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 8457;
    private static final int TIMEOUT = 5000;

    public static TTransport getTTransport() {
        TTransport transport = new TFramedTransport(new TSocket(SERVER_IP, SERVER_PORT), TIMEOUT);
        return transport;
    }

    public static TProtocol getTProtocol(TTransport transport) {
        TProtocol protocol = new TCompactProtocol(transport);
        return protocol;
    }
}
