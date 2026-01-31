package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap ngm;

    public HistoryHandler(NGramMap map) {
        this.ngm = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        List<String> labels = new ArrayList<>();
        List<TimeSeries> ts = new ArrayList<>();

        for (String word : words) {
            labels.addLast(word);
            ts.addLast(ngm.weightHistory(word, q.startYear(), q.endYear()));
        }

        XYChart chart = Plotter.generateTimeSeriesChart(labels, ts);
        return Plotter.encodeChartAsString(chart);
    }
}
