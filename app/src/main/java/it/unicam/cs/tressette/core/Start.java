/*
 * MIT License
 *
 * Copyright (c) 2024 gitvanni
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package it.unicam.cs.tressette.core;

import it.unicam.cs.tressette.dataanalysis.TressetteDataCollector;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.App;

public class Start extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Creiamo la scena principale con un layout verticale
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        // Simuliamo la mano dell'avversario (carte coperte)
        HBox avversarioMano = new HBox(10);
        avversarioMano.setAlignment(Pos.CENTER);
        for (int i = 0; i < 10; i++) {
            ImageView cartaCoperta = createCardBack();
            avversarioMano.getChildren().add(cartaCoperta);
        }

        // Creiamo l'etichetta per l'avversario
        Label avversarioLabel = new Label("Mano dell'Avversario");
        avversarioLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Simuliamo il mazzo di carte coperto al centro del tavolo
        ImageView mazzoCoperto = createCardBack();
        Label mazzoLabel = new Label("Mazzo di Carte Coperto");

        // Creiamo una riga per il mazzo
        VBox mazzoContainer = new VBox(10, mazzoCoperto, mazzoLabel);
        mazzoContainer.setAlignment(Pos.CENTER);

        // Simuliamo le prese del giocatore (carte coperte)
        ImageView preseGiocatore = createCardBack();
        Label preseGiocatoreLabel = new Label("Prese Giocatore");

        // Simuliamo le prese dell'avversario (carte coperte)
        ImageView preseAvversario = createCardBack();
        Label preseAvversarioLabel = new Label("Prese Avversario");

        // Layout per le pile delle prese
        HBox preseContainer = new HBox(50, new VBox(preseAvversario, preseAvversarioLabel), mazzoContainer, new VBox(preseGiocatore, preseGiocatoreLabel));
        preseContainer.setAlignment(Pos.CENTER);

        // Simuliamo la mano del giocatore (carte visibili)
        HBox giocatoreMano = new HBox(10);
        giocatoreMano.setAlignment(Pos.CENTER);

        // Aggiungiamo alcune carte (esempio con carte fittizie)
        for (int i = 1; i <= 1; i++) {
            ImageView cartaVisibile = createCardFront("card_" + i + ".png");
            giocatoreMano.getChildren().add(cartaVisibile);
        }

        // Creiamo l'etichetta per il giocatore
        Label giocatoreLabel = new Label("La tua Mano");
        giocatoreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Aggiungiamo tutti gli elementi al layout principale
        root.getChildren().addAll(avversarioLabel, avversarioMano, preseContainer, giocatoreLabel, giocatoreMano);

        // Creiamo la scena e mostriamo la finestra
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Tressette - Istantanea Partita");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Funzione per creare una carta coperta (retro del mazzo)
    private ImageView createCardBack() {
        Image backImage = new Image(getClass().getResourceAsStream("/images/card_back.png"));
        ImageView imageView = new ImageView(backImage);
        imageView.setFitHeight(100);
        imageView.setFitWidth(70);
        return imageView;
    }

    // Funzione per creare una carta scoperta (davanti del mazzo)
    private ImageView createCardFront(String cardImageFile) {
        Image frontImage = new Image(getClass().getResourceAsStream("/images/" + cardImageFile));
        ImageView imageView = new ImageView(frontImage);
        imageView.setFitHeight(100);
        imageView.setFitWidth(70);
        return imageView;
    }

    public static void main(String[] args) {
        launch(args);
    }
/*
    public static void main(String[] args) {
        System.out.println("benvenuti al tressette");
        TressetteGame game = new TressetteGame();
        TressetteDataCollector collector = new TressetteDataCollector();
        TressetteGameState current = game.getCurrentState();
        int currentPlayer = 0;
        do{
            System.out.println("E' il turno: "+current.getTurn());
            currentPlayer = current.getCurrentPlayer();
            System.out.println("E' il turno del giocatore"+currentPlayer);
            long startTime = System.nanoTime();
            TressetteAction action = game.getPlayers().get(currentPlayer).getAction(current);
            long endTime = System.nanoTime();
            long decisionTime = (endTime - startTime) / 1_000_000; // In millisecondi
            System.out.println("Carta giocata: "+action.getCard());
            current = (TressetteGameState) current.applyAction(action);
            collector.collectData(current,action,currentPlayer,decisionTime);
        }while(!current.isTerminalNode());
        collector.collectScore(game);
        System.out.println("La partita è finita");
        System.out.println("Il giocatore 0 ha fatto punti: "+current.getPlayerScore(0));
        System.out.println("Il giocatore 1 ha fatto punti: "+current.getPlayerScore(1));
        System.out.println("Il giocatore 0 ha utility: "+current.getUtility(0));
        System.out.println("Il giocatore 1 ha utility: "+current.getUtility(1));
    }*/
}

