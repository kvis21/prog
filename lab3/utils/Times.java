package utils;

public enum Times {
    TIMETOIME("время от времени"),
    NEVER("никогда не"),
    THEN("затем"),
    FINALLY("наконец,"),
    LONGERTHANEXPECT("гораздо дольше, чем рассчитывал"),
    SIXSIX("Шестого ноября, в шестой год ");


    private String time;

    Times(String time) {
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }

}
