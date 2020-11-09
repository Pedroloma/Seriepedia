package com.example.application.views.helloworld;

import com.example.application.backend.Result;
import com.example.application.backend.Search;
import com.example.application.backend.Season;
import com.example.application.backend.TVSeries;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Route(value = "hello", layout = MainView.class)
@PageTitle("Hello World")
@CssImport("./styles/views/helloworld/hello-world-view.css")
@RouteAlias(value = "", layout = MainView.class)
public class HelloWorldView<callSearchService> extends HorizontalLayout {

    private static final Logger log = LoggerFactory.getLogger(HelloWorldView.class);

    private final TextField name;
    private final Button search;

    public HelloWorldView() {
        setId("hello-world-view");
        name = new TextField("Enter text for search");
        search = new Button("Search");
        setVerticalComponentAlignment(Alignment.END, name, search);

        add(name, search);

        search.addClickListener(e -> {
            Search search = callSearchService(name.getValue());
            getChildren().filter(component -> component instanceof Details).forEach(this::remove);
            for (Result result : search.getResults()) {
                Details details = new Details(result.getName(), null);
                details.setId(String.valueOf(result.getId()));
                add(details);
                details.addOpenedChangeListener(event -> {
                    if (event.isOpened()) {
                        TVSeries tvSeries = callSeriesService(Integer.parseInt(event.getSource().getId().orElse("-1")));
                        TreeGrid<TVSeries> tvSeriesTreeGrid = new TreeGrid<>();
                        tvSeriesTreeGrid.addHierarchyColumn(TVSeries::getOverview).setHeader("Overview");
                        tvSeriesTreeGrid.addColumn(TVSeries::getFirstAirDate).setHeader("First air date");
                        tvSeriesTreeGrid.addColumn(TVSeries::getLastAirDate).setHeader("Last air date");
                        tvSeriesTreeGrid.addColumn(TVSeries::getNumberOfSeasons).setHeader("Number of seasons");
                        tvSeriesTreeGrid.addColumn(TVSeries::getNumberOfEpisodes).setHeader("Number of episodes");
                        tvSeriesTreeGrid.setItems(tvSeries);
                        tvSeriesTreeGrid.setId(String.valueOf(tvSeries.getId()));
                        details.setContent(tvSeriesTreeGrid);
                    }
                });
            }
        });
    }

    private Search callSearchService(String searchString) {
        RestOperations restTemplate = new RestTemplate();
        Search search = restTemplate.getForObject("https://api.themoviedb.org/3/search/tv?api_key=f47d6a508147e24feac59bf46973aac2&language=en-US&page=1&query=" + searchString + "&include_adult=false", Search.class);
        log.info(search.toString());
        return search;
    }

    private TVSeries callSeriesService(int seriesId) {
        RestOperations restTemplate = new RestTemplate();
        TVSeries tvSeries = restTemplate.getForObject("https://api.themoviedb.org/3/tv/" + seriesId + "?api_key=f47d6a508147e24feac59bf46973aac2&language=en-US", TVSeries.class);
        log.info(tvSeries.toString());
        return tvSeries;
    }

    private Season callSeasonService(int tvSeriesId, int seasonNumber) {
        TVSeries tvSeries = new TVSeries();
//        for (int i = 1; i <= 8; i++) {
        RestOperations restTemplate = new RestTemplate();
        Season season = restTemplate.getForObject("https://api.themoviedb.org/3/tv/" + tvSeriesId + "/season/" + seasonNumber
                + "?api_key=f47d6a508147e24feac59bf46973aac2&language=en-US", Season.class);
        tvSeries.getSeasons().add(season);
        log.info("{}", season);
        return season;
//        }
    }
}