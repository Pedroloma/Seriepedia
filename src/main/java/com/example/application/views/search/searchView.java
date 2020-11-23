package com.example.application.views.search;

import com.example.application.backend.entity.*;
import com.example.application.backend.service.SearchService;
import com.example.application.backend.service.SeasonService;
import com.example.application.backend.service.SeriesService;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Route(value = "hello", layout = MainView.class)
@PageTitle("Seriepedia")
@CssImport("./styles/views/searchview/search-view.css")
@RouteAlias(value = "", layout = MainView.class)
public class searchView extends HorizontalLayout {

    private final TextField name;
    @Autowired
    SearchService searchService;
    @Autowired
    SeasonService seasonService;
    @Autowired
    SeriesService seriesService;
    @Value("${api.key}")
    private String apiKey;

    public searchView() {
        setId("hello-world-view");
        name = new TextField();
        name.setPlaceholder("Enter text to search");
        name.setClearButtonVisible(true);
        Button search1 = new Button("Search");
        search1.addClickShortcut(Key.ENTER);
        setVerticalComponentAlignment(Alignment.END, name, search1);

        add(name, search1);

        search1.addClickListener(e -> {
            if (name.getValue().isEmpty())
                Notification.show("Enter text to search!!!");
            else {

                Search search = searchService.callSearchService(name.getValue());
                getChildren().filter(component -> component instanceof Details).forEach(this::remove);
                for (Result result : search.getResults()) {
                    Details details = new Details(result.getName(), null);
                    details.setId(String.valueOf(result.getId()));
                    add(details);
                    details.addOpenedChangeListener(event -> {
                        if (event.isOpened()) {
                            TVSeries tvSeries = seriesService.callSeriesService(Integer.parseInt(event.getSource().getId().orElse("-1")));
                            TextArea textOverview = new TextArea("Overview");
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

                            Grid<Season> seasonGrid = new Grid<>();
                            seasonGrid.setItems(tvSeries.getSeasons());
                            seasonGrid.addColumn(Season::getSeasonNumber).setHeader("Season");
                            seasonGrid.addColumn(new LocalDateRenderer<>(Season::getAirDate, DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))).setHeader("Air date");
                            seasonGrid.addColumn(Season::getEpisodeCount).setHeader("Number of episodes");
                            seasonGrid.addComponentColumn(this::createPosterButton).setHeader("Poster");
                            seasonGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
                            seasonGrid.addComponentColumn(season -> createDetailButton(tvSeries.getId(), season)).setHeader("Details");
                            seasonGrid.setHeight(((tvSeries.getSeasons().size() + 1) * 54) + "px");

                            VerticalLayout verticalLayout = new VerticalLayout(horizontalLayout, seasonGrid);
                            verticalLayout.setPadding(false);
                            verticalLayout.setSpacing(false);
                            details.setContent(verticalLayout);
                        }
                    });
                }
            }
        });
    }

    private Component createDetailButton(int serieTVId, Season season) {
        return new Button("...", clickEvent -> {
            Dialog dialog = new Dialog();

            dialog.setWidth("750px");
            dialog.setHeight("600px");
            dialog.setResizable(true);

            Label title = new Label("Season " + season.getSeasonNumber());

            Season season1 = seasonService.callSeasonService(serieTVId, season.getSeasonNumber());

            Grid<Episode> episodeGrid = new Grid<>();
            episodeGrid.setItems(season1.getEpisodes());
            episodeGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
            episodeGrid.addColumn(Episode::getEpisodeNumber, "EpisodeNumber").setHeader("Episode number");
            episodeGrid.addColumn(Episode::getName, "Name").setHeader("Name");
            episodeGrid.addColumn(new LocalDateRenderer<>(Episode::getAirDate, DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))).setHeader("Air date");
            episodeGrid.getColumns().forEach(personColumn -> personColumn.setAutoWidth(true));
            episodeGrid.setHeight("95%");

            dialog.add(title, episodeGrid);
            dialog.open();
        });
    }

    private Component createPosterButton(Season item) {
        return new Button("Poster", clickEvent -> {
            Dialog dialog = new Dialog();

            dialog.setWidth("550px");
            dialog.setHeight("775px");

            Image image = new Image();
            image.setSrc("https://image.tmdb.org/t/p/w500" + item.getPosterPath());

            dialog.add(image);

            dialog.open();
        });
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}