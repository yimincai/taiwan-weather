package yimincai.net.taiwanweather;

public class Forecast {
    private String time;
    private String wx;
    private String pop;
    private String minT;
    private String maxT;
    private String ci;

    public Forecast() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWx() {
        return wx;
    }


    public void setWx(String wx) {
        this.wx = wx;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getMinT() {
        return minT;
    }

    public void setMinT(String minT) {
        this.minT = minT;
    }

    public String getMaxT() {
        return maxT;
    }

    public void setMaxT(String maxT) {
        this.maxT = maxT;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public Forecast(String time, String wx, String pop, String minT, String maxT, String ci) {
        this.time = time;
        this.wx = wx;
        this.pop = pop;
        this.minT = minT;
        this.maxT = maxT;
        this.ci = ci;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "time='" + time + '\'' +
                ", wx='" + wx + '\'' +
                ", pop='" + pop + '\'' +
                ", minT='" + minT + '\'' +
                ", maxT='" + maxT + '\'' +
                ", ci='" + ci + '\'' +
                '}';
    }
}
