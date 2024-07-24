package com.sys.manager.utils.text;


/**
 * 提供通用唯一识别码（universally unique identifier）（UUID）实现
 *
 * @author lichp
 */
public class UUID {
    private static final long serialVersionUID = -1185015143654744140L;


    /**
     * 此UUID的最高64有效位
     */
    private final long mostSigBits;

    /**
     * 此UUID的最低64有效位
     */
    private final long leastSigBits;


    /**
     * 功能：去掉UUID中的“-”
     */
    public static String replaceUUID(String uuid) {
        uuid = uuid.replaceAll("\\-", "");
        return uuid;
    }
    /**
     * 功能：获取UUID并去除“-”
     */
    public static String getUUID() {
        String uuid = java.util.UUID.randomUUID().toString();
        return uuid.replaceAll("\\-", "");
    }

    /**
     * 私有构造
     *
     * @param data 数据
     */
    private UUID(byte[] data) {
        long msb = 0;
        long lsb = 0;
        assert data.length == 16 : "data must be 16 bytes in length";
        for (int i = 0; i < 8; i++) {
            msb = (msb << 8) | (data[i] & 0xff);
        }
        for (int i = 8; i < 16; i++) {
            lsb = (lsb << 8) | (data[i] & 0xff);
        }
        this.mostSigBits = msb;
        this.leastSigBits = lsb;
    }

    /**
     * 使用指定的数据构造新的 UUID。
     *
     * @param mostSigBits  用于 {@code UUID} 的最高有效 64 位
     * @param leastSigBits 用于 {@code UUID} 的最低有效 64 位
     */
    public UUID(long mostSigBits, long leastSigBits) {
        this.mostSigBits = mostSigBits;
        this.leastSigBits = leastSigBits;
    }
}
