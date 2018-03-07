/**
 * 
 */
package com.fasimito.fabric;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Set;

import org.bouncycastle.util.encoders.Hex;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;

import io.netty.util.internal.StringUtil;

/**
 * �����û�����
 * @author fasimito
 *
 * @date 2018��2��23�� - ����4:36:53
 * @email fasimito@163.com
 */
class FabricUser implements User, Serializable {

    private static final long serialVersionUID = 5695080465408336815L;

    /** ���� */
    private String name;
    /** ���� */
    private Set<String> roles;
    /** �˻� */
    private String account;
    /** �������� */
    private String affiliation;
    /** ��֯ */
    private String organization;
    /** ע���������?? */
    private String enrollmentSecret;
    /** ��Աid */
    private String mspId;
    /** ע��Ǽǲ��� */
    Enrollment enrollment = null; // ??Ҫ�ڲ���env�з�??

    /** �洢���ö��� */
    private transient FabricStore keyValStore;
    private String keyValStoreName;

    public FabricUser(String name, String org, FabricStore store) {
        this.name = name;
        this.keyValStore = store;
        this.organization = org;
        this.keyValStoreName = toKeyValStoreName(this.name, org);

        String memberStr = keyValStore.getValue(keyValStoreName);
        if (null != memberStr) {
            saveState();
        } else {
            restoreState();
        }
    }

    /**
     * �����˻���Ϣ�����û�״???�������洢���ö���
     * 
     * @param account
     *            �˻�
     */
    public void setAccount(String account) {
        this.account = account;
        saveState();
    }

    @Override
    public String getAccount() {
        return this.account;
    }

    /**
     * ���ô���������Ϣ�����û�״???�������洢���ö���
     * 
     * @param affiliation
     *            ��������
     */
    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
        saveState();
    }

    @Override
    public String getAffiliation() {
        return this.affiliation;
    }

    @Override
    public Enrollment getEnrollment() {
        return this.enrollment;
    }

    /**
     * ���û�Աid��Ϣ�����û�״???�������洢���ö���
     * 
     * @param mspID
     *            ��Աid
     */
    public void setMspId(String mspID) {
        this.mspId = mspID;
        saveState();
    }

    @Override
    public String getMspId() {
        return this.mspId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    /**
     * ���ù�����Ϣ�����û�״???�������洢���ö���
     * 
     * @param roles
     *            ����
     */
    public void setRoles(Set<String> roles) {
        this.roles = roles;
        saveState();
    }

    @Override
    public Set<String> getRoles() {
        return this.roles;
    }

    public String getEnrollmentSecret() {
        return enrollmentSecret;
    }

    /**
     * ����ע���������Կ��Ϣ�����û�״̬�������洢���ö���
     * 
     * @param enrollmentSecret
     *            ע���������??
     */
    public void setEnrollmentSecret(String enrollmentSecret) {
        this.enrollmentSecret = enrollmentSecret;
        saveState();
    }

    /**
     * ����ע��Ǽǲ�����Ϣ�����û�״???�������洢���ö���
     * 
     * @param enrollment
     *            ע��Ǽǲ���
     */
    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
        saveState();
    }

    /**
     * ȷ����������Ƿ���ע??
     * 
     * @return ���
     */
    public boolean isRegistered() {
        return !StringUtil.isNullOrEmpty(enrollmentSecret);
    }

    /**
     * ȷ����������Ƿ��Ѿ�ע��
     *
     * @return ���
     */
    public boolean isEnrolled() {
        return this.enrollment != null;
    }

    /** ���û�״̬�������洢���ö��� */
    public void saveState() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            oos.flush();
            keyValStore.setValue(keyValStoreName, Hex.toHexString(bos.toByteArray()));
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * �Ӽ�ֵ�洢�лָ����û���״???(����ҵ��Ļ�)������Ҳ�����ʲôҲ��Ҫ��???
     * 
     * @return �����û�
     */
    private FabricUser restoreState() {
        String memberStr = keyValStore.getValue(keyValStoreName);
        if (null != memberStr) {
            // �û��ڼ�ֵ�洢�б��ҵ�����˻ָ�״??????
            byte[] serialized = Hex.decode(memberStr);
            ByteArrayInputStream bis = new ByteArrayInputStream(serialized);
            try {
                ObjectInputStream ois = new ObjectInputStream(bis);
                FabricUser state = (FabricUser) ois.readObject();
                if (state != null) {
                    this.name = state.name;
                    this.roles = state.roles;
                    this.account = state.account;
                    this.affiliation = state.affiliation;
                    this.organization = state.organization;
                    this.enrollmentSecret = state.enrollmentSecret;
                    this.enrollment = state.enrollment;
                    this.mspId = state.mspId;
                    return this;
                }
            } catch (Exception e) {
                throw new RuntimeException(String.format("Could not restore state of member %s", this.name), e);
            }
        }
        return null;
    }

    public static String toKeyValStoreName(String name, String org) {
        System.out.println("toKeyValStoreName = " + "user." + name + org);
        return "user." + name + org;
    }

}