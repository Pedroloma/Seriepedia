package com.example.application.views.helloworld;

import com.example.application.backend.Result;
import com.example.application.backend.Search;
import com.example.application.backend.Season;
import com.example.application.backend.TVSeries;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;

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
                        TextField textOverview = new TextField("Overview");
                        textOverview.setReadOnly(true);
                        textOverview.setValue(tvSeries.getOverview());
                        textOverview.getElement().setAttribute("colspan", Integer.toString(4));
                        TextField textFirstAirDate = new TextField("First air date");
                        textFirstAirDate.setReadOnly(true);
                        if (tvSeries.getFirstAirDate() != null) {
                            textFirstAirDate.setValue(tvSeries.getFirstAirDate().format(DateTimeFormatter.ofPattern("dd LLLL yyyy")));
                        }
                        TextField textLastAirDate = new TextField("Last air date");
                        textLastAirDate.setReadOnly(true);
                        if (tvSeries.getLastAirDate() != null) {
                            textLastAirDate.setValue(tvSeries.getLastAirDate().format(DateTimeFormatter.ofPattern("dd LLLL yyyy")));
                        }
                        TextField numberOfSeasons = new TextField("Number of seasons");
                        numberOfSeasons.setReadOnly(true);
                        numberOfSeasons.setValue(String.valueOf(tvSeries.getNumberOfSeasons()));
                        TextField numberOfEpisodes = new TextField("Number of episodes");
                        numberOfEpisodes.setReadOnly(true);
                        numberOfEpisodes.setValue(String.valueOf(tvSeries.getNumberOfEpisodes()));

                        FormLayout form = new FormLayout(textOverview, textFirstAirDate, textLastAirDate, numberOfSeasons, numberOfEpisodes);
                        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4));
                        form.setWidthFull();

                        HorizontalLayout horizontalLayout = new HorizontalLayout(form);
                        horizontalLayout.setWidthFull();

                        TreeGrid<Season> seasonTreeGrid = new TreeGrid<>();
                        seasonTreeGrid.setItems(tvSeries.getSeasons());
                        seasonTreeGrid.addHierarchyColumn(Season::getSeasonNumber).setHeader("Season");
                        seasonTreeGrid.addColumn(season -> season.getAirDateFormatted("dd LLLL yyyy")).setHeader("Air date");
                        seasonTreeGrid.addColumn(Season::getEpisodeCount).setHeader("Episodes");
                        VerticalLayout verticalLayout = new VerticalLayout(horizontalLayout, seasonTreeGrid);
                        verticalLayout.setPadding(false);
                        verticalLayout.setSpacing(false);
                        verticalLayout.setHeight((tvSeries.getSeasons().size() * 36) + 211 + "px");
                        details.setContent(verticalLayout);
                    }
                });
            }
        });
    }

    private Search callSearchService(String searchString) {
        RestOperations restTemplate = new RestTemplate();
        Search search = restTemplate.getForObject("https://api.themoviedb.org/3/search/tv?api_key=f47d6a508147e24feac59bf46973aac2&language=en-US&page=1&query=" + searchString + "&include_adult=false", Search.class);
        log.info(search != null ? search.toString() : null);
        return search;
    }

    private TVSeries callSeriesService(int seriesId) {
        RestOperations restTemplate = new RestTemplate();
        TVSeries tvSeries = restTemplate.getForObject("https://api.themoviedb.org/3/tv/" + seriesId + "?api_key=f47d6a508147e24feac59bf46973aac2&language=en-US", TVSeries.class);
        log.info(tvSeries != null ? tvSeries.toString() : null);
        return tvSeries;
    }

    private Season callSeasonService(int tvSeriesId, int seasonNumber) {
        TVSeries tvSeries = new TVSeries();
        RestOperations restTemplate = new RestTemplate();
        Season season = restTemplate.getForObject("https://api.themoviedb.org/3/tv/" + tvSeriesId + "/season/" + seasonNumber
                + "?api_key=f47d6a508147e24feac59bf46973aac2&language=en-US", Season.class);
        tvSeries.getSeasons().add(season);
        log.info("{}", season);
        return season;
    }

}