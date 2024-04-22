import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class myFrame extends JFrame {

    ArrayList<Card> dealerHand;
    ArrayList<Card> playerHand;
    int cardWidth = 100; // ratio 1 / 1.4
    int cardHeight = 140;
    int playerS;
    int playerA;
    int dealerS;
    int dealerA;
    Card hiddenC;
    boolean gameRunning = true;

    JPanel gamePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {

            super.paintComponent(g);

            // draw hidden card
            try {
                // draw hidden card
                Image hiddenCardImg = new ImageIcon(getClass().getResource("./photos/BACK.PNG")).getImage();
                if(!stayButton.isEnabled()){
                    hiddenCardImg = new ImageIcon(getClass().getResource(hiddenC.getImagePath())).getImage();
                }
                g.drawImage(hiddenCardImg, 20, 20, cardWidth, cardHeight, null);

                //draw dealer's hand
                for (int i = 0; i < dealerHand.size(); i++) {

                    Card card = dealerHand.get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, cardWidth + 25 + (cardWidth + 5)*i, 20, cardWidth, cardHeight, null);

                }

                //draw player's hand

                for(int i = 0; i < playerHand.size(); i++){

                    Card card = playerHand.get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, 20 + (cardWidth + 5)*i, 320, cardWidth, cardHeight, null);

                }

                if(!stayButton.isEnabled()){
                    dealerS = reduceDealerAce();
                    playerS = reducePlayerAce();
                    System.out.println("STAY: ");
                    System.out.println(dealerS);
                    System.out.println(playerS);

                    String message = "";
                    if(playerS > 21){
                        message = "You Lose!";
                    }
                    else if(dealerS > 21){
                        message = "You Win!";
                    }
                    else if(playerS == dealerS){
                        message = "Tie!";
                    }
                    else if(playerS > dealerS){
                        message = "You Win!";
                    }
                    else if(playerS < dealerS){
                        message = "You Lose!";
                    }
                    gameRunning = false;
                    g.setFont(new Font("Arial", Font.PLAIN, 30));
                    g.setColor(Color.WHITE);
                    g.drawString(message, 220, 250);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    JPanel buttonPanel = new JPanel();
    JButton hitButton = new JButton("Hit");
    JButton stayButton = new JButton("Stay");
    JButton newButton = new JButton("New Game");

    public myFrame(ArrayList<Card> dealerHand, ArrayList<Card> playerHand, ArrayList<Card> deck, int playerSum, int playerAceCount, int dealerSum, int dealerAceCount, Card hiddenCard) {
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
        playerS = playerSum;
        playerA = playerAceCount;
        dealerS = dealerSum;
        dealerA = dealerAceCount;
        hiddenC = hiddenCard;
        this.setTitle("Blackjack");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(53, 101, 77));
        this.add(gamePanel);

        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);
        stayButton.setFocusable(false);
        buttonPanel.add(stayButton);
        newButton.setFocusable(false);
        newButton.setEnabled(false);
        buttonPanel.add(newButton);

        this.add(buttonPanel, BorderLayout.SOUTH);

        newButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                Blackjack.resetGame();

            }
        });

        hitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Card card = deck.remove(deck.size()-1);
                playerS += card.getValue();
                playerA += card.isAce() ? 1:0;
                playerHand.add(card);

                if(reducePlayerAce() > 21){ // A + 2 + J == 23 -> A + 2 + J = 13
                    hitButton.setEnabled(false);
                }

                gamePanel.repaint();
            }
        });

        stayButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);
                newButton.setEnabled(true);

                while(dealerS < 17){
                    Card card = deck.remove(deck.size() - 1);
                    dealerS += card.getValue();
                    dealerA += card.isAce() ? 1:0;
                    dealerHand.add(card);
                }
                gamePanel.repaint();
            }
        });

        gamePanel.repaint();

    }

    public int reducePlayerAce(){
        while (this.playerS > 21 && playerA > 0){
            playerS -= 10;
            playerA -= 1;
        }
        return playerS;
    }

    public int reduceDealerAce(){
        while (this.dealerS > 21 && dealerA > 0){
            dealerS -= 10;
            dealerA -= 1;
        }
        return dealerS;
    }

    public void updateGameState(ArrayList<Card> dealerHand, ArrayList<Card> playerHand, ArrayList<Card> deck, int playerSum, int playerAceCount, int dealerSum, int dealerAceCount, Card hiddenCard){

        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
        playerS = playerSum;
        playerA = playerAceCount;
        dealerS = dealerSum;
        dealerA = dealerAceCount;
        hiddenC = hiddenCard;
        hitButton.setEnabled(true);
        stayButton.setEnabled(true);
        newButton.setEnabled(false);


        hitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Card card = deck.remove(deck.size() - 1);
                playerS += card.getValue();
                playerA += card.isAce() ? 1:0;
                playerHand.add(card);

                if(reducePlayerAce() > 21){ // A + 2 + J == 23 -> A + 2 + J = 13
                    hitButton.setEnabled(false);
                }

                gamePanel.repaint();
            }
        });


        stayButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);
                newButton.setEnabled(true);

                while(dealerS < 17){
                    Card card = deck.remove(deck.size() - 1);
                    dealerS += card.getValue();
                    dealerA += card.isAce() ? 1:0;
                    dealerHand.add(card);
                }
                gamePanel.repaint();
            }
        });

        gamePanel.repaint();

        newButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                Blackjack.resetGame();

            }
        });

        JPanel gamePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {

                super.paintComponent(g);

                // draw hidden card
                try {
                    // draw hidden card
                    Image hiddenCardImg = new ImageIcon(getClass().getResource("./photos/BACK.PNG")).getImage();
                    if(!stayButton.isEnabled()){
                        hiddenCardImg = new ImageIcon(getClass().getResource(hiddenC.getImagePath())).getImage();
                    }
                    g.drawImage(hiddenCardImg, 20, 20, cardWidth, cardHeight, null);

                    //draw dealer's hand
                    for (int i = 0; i < dealerHand.size(); i++) {

                        Card card = dealerHand.get(i);
                        Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                        g.drawImage(cardImg, cardWidth + 25 + (cardWidth + 5)*i, 20, cardWidth, cardHeight, null);

                    }

                    //draw player's hand

                    for(int i = 0; i < playerHand.size(); i++){

                        Card card = playerHand.get(i);
                        Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                        g.drawImage(cardImg, 20 + (cardWidth + 5)*i, 320, cardWidth, cardHeight, null);

                    }

                    if(!stayButton.isEnabled()){
                        dealerS = reduceDealerAce();
                        playerS = reducePlayerAce();
                        System.out.println("STAY: ");
                        System.out.println(dealerS);
                        System.out.println(playerS);

                        String message = "";
                        if(playerS > 21){
                            message = "You Lose!";
                        }
                        else if(dealerS > 21){
                            message = "You Win!";
                        }
                        else if(playerS == dealerS){
                            message = "Tie!";
                        }
                        else if(playerS > dealerS){
                            message = "You Win!";
                        }
                        else if(playerS < dealerS){
                            message = "You Lose!";
                        }
                        gameRunning = false;
                        g.setFont(new Font("Arial", Font.PLAIN, 30));
                        g.setColor(Color.WHITE);
                        g.drawString(message, 220, 250);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
    }


}



