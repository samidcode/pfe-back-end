package ma.payment.charts;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Overview {
    @JsonProperty("this-week")
    private WeekInfo thisWeek;

    @JsonProperty("last-week")
    private WeekInfo lastWeek;

    public WeekInfo getThisWeek() {
        return thisWeek;
    }

    public void setThisWeek(WeekInfo thisWeek) {
        this.thisWeek = thisWeek;
    }

    public WeekInfo getLastWeek() {
        return lastWeek;
    }

    public void setLastWeek(WeekInfo lastWeek) {
        this.lastWeek = lastWeek;
    }
}
