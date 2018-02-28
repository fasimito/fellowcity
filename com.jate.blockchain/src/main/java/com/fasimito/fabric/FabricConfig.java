package com.fasimito.fabric;

import java.io.File;

import org.apache.log4j.Logger;

import com.fasimito.fabric.bean.Chaincode;
import com.fasimito.fabric.bean.Orderers;
import com.fasimito.fabric.bean.Peers;

public class FabricConfig {

    private static Logger log = Logger.getLogger(FabricConfig.class);

    /** the peer nodes in the blockchain */
    private Peers peers;
    /** the orderer service node in the blockchain */
    private Orderers orderers;
    /** the chaincode */
    private Chaincode chaincode;
    /** channel-artifacts:
     * in this path stored 
     * Ĭ��channel-artifacts����·��/xxx/WEB-INF/classes/fabric/channel-artifacts/ */
    private String channelArtifactsPath;
    /** crypto-config����·����
     * Ĭ��crypto-config����·��/xxx/WEB-INF/classes/fabric/crypto-config/ */
    private String cryptoConfigPath;
    private boolean registerEvent = false;

    public FabricConfig() {
        /**
         *  Ĭ��channel-artifacts����·�� /xxx/WEB-INF/classes/fabric/channel-artifacts/
         *  ��·���´洢�˴�����顢Ƶ�������ļ���ê�ڵ㶨���ļ�
         */
        channelArtifactsPath = getChannlePath() + "/channel-artifacts/";
        /**
         * Ĭ��crypto-config����·�� /xxx/WEB-INF/classes/fabric/crypto-config/
         * ��·���´��֤�����Կ
         */
        cryptoConfigPath = getChannlePath() + "/crypto-config/";
    }

    /**
     * Ĭ��fabric����·��
     * 
     * @return D:/installSoft/apache-tomcat-9.0.0.M21-02/webapps/xxx/WEB-INF/classes/fabric/channel-artifacts/
     */
    private String getChannlePath() {
        String directorys = ChaincodeManager.class.getClassLoader().getResource("fabric").getFile();
        log.debug("directorys = " + directorys);
        File directory = new File(directorys);
        log.debug("directory = " + directory.getPath());

        return directory.getPath();
        // return "src/main/resources/fabric/channel-artifacts/";
    }

    public Peers getPeers() {
        return peers;
    }

    public void setPeers(Peers peers) {
        this.peers = peers;
    }

    public Orderers getOrderers() {
        return orderers;
    }

    public void setOrderers(Orderers orderers) {
        this.orderers = orderers;
    }

    public Chaincode getChaincode() {
        return chaincode;
    }

    public void setChaincode(Chaincode chaincode) {
        this.chaincode = chaincode;
    }

    public String getChannelArtifactsPath() {
        return channelArtifactsPath;
    }

    public void setChannelArtifactsPath(String channelArtifactsPath) {
        this.channelArtifactsPath = channelArtifactsPath;
    }

    public String getCryptoConfigPath() {
        return cryptoConfigPath;
    }

    public void setCryptoConfigPath(String cryptoConfigPath) {
        this.cryptoConfigPath = cryptoConfigPath;
    }

    public boolean isRegisterEvent() {
        return registerEvent;
    }

    public void setRegisterEvent(boolean registerEvent) {
        this.registerEvent = registerEvent;
    }

}