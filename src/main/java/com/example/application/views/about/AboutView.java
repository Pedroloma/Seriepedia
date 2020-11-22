package com.example.application.views.about;

import com.example.application.views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "about", layout = MainView.class)
@PageTitle("About")
@CssImport("./styles/views/about/about-view.css")
public class AboutView extends Div {

    public AboutView() {
        Span span = new Span();
        span.setText("This website is only for learning and practising the Vaadin framework.");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        setId("about-view");
        Label label = new Label("All data in this web is provided by \"themoviedb.org\"");
        Image image = new Image();
        image.setSrc("images/the_movie_db.svg");
        horizontalLayout.add(label, image);
        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        add(span, horizontalLayout);
    }

}
