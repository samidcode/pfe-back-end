package ma.payment.dto;

import ma.payment.charts.ChartSeries;
import ma.payment.charts.Overview;

import java.util.List;
import java.util.Map;

public class ChartsDTO {
    private Overview overview;
    private List<String> labels;
    private Map<String, List<ChartSeries>> series;

    public Overview getOverview() {
        return overview;
    }

    public void setOverview(Overview overview) {
        this.overview = overview;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public Map<String, List<ChartSeries>> getSeries() {
        return series;
    }

    public void setSeries(Map<String, List<ChartSeries>> series) {
        this.series = series;
    }
}
