import java.util.*;

public class PokerHand {
  public enum Result { TIE, WIN, LOSS }

  static final int HAND_SIZE = 5;
  Card[] hand = new Card[HAND_SIZE];
  Rank rank;

  public PokerHand(String hand) {
    String[] cardStrings = hand.split(" ");
    for (int i = 0; i < cardStrings.length; i++) {
      this.hand[i] = new Card(cardStrings[i]);
    }

    rank = new Rank(this);
  }

  public Result compareWith(PokerHand hand) {
    int result = rank.compareTo(hand.rank);

    if (result > 0) {
      return Result.WIN;
    } else if (result < 0) {
      return Result.LOSS;
    } else {
      return Result.TIE;
    }

  }

  class Rank {
    int outer = 0;
    List<Integer> comparisonCards = new ArrayList<Integer>();
    RankType rankType;
    Map<Integer, Integer> counts;

    public Rank(PokerHand hand) {
      Card[] cards = hand.hand;
      counts = getCounts(cards);

      if (allSameSuit(cards) && inARow(cards)) {
        if (isRoyal(cards)) {
          rankType = RankType.ROYAL_FLUSH;
        } else {
          rankType = RankType.STRAIGHT_FLUSH;
        }
      } else if (is4Kind(cards)) {
        rankType = RankType.FOUR_OF_A_KIND;
      } else if (isFullHouse(cards)) {
        rankType = RankType.FULL_HOUSE;
      } else if (allSameSuit(cards)) {
        rankType = RankType.FLUSH;
      } else if (inARow(cards)) {
        rankType = RankType.STRAIGHT;
      } else if (is3Kind(cards)) {
        rankType = RankType.THREE_OF_A_KIND;
      } else if (is2Pair(cards)) {
        rankType = RankType.TWO_PAIR;
      } else if (is1Pair(cards)) {
        rankType = RankType.ONE_PAIR;
      } else {
        isHighCard(cards);
        rankType = RankType.HIGH_CARD;
      }

      this.outer = rankType.rankOrder;
    }

    public int compareTo(Rank otherRank) {
      final int win = 1;
      final int loss = -1;
      final int tie = 0;

      if (this.outer > otherRank.outer) {
        return win;
      } else if (this.outer < otherRank.outer) {
        return loss;
      } else {
        // they must ahve the same number of comparison cards
        for (int i = 0; i < this.comparisonCards.size(); i++) {
          int myCard = this.comparisonCards.get(i);
          int oppCard = otherRank.comparisonCards.get(i);
          if (myCard > oppCard) {
            return win;
          } else if (myCard < oppCard) {
            return loss;
          }
        }
        return tie;
      }

    }

    private Map<Integer, Integer> getCounts(Card[] hand) {
      Map<Integer, Integer> counts = new HashMap<>();
      for (int i = 0; i < hand.length; i++) {
        int val = hand[i].value;
        if (counts.containsKey(val)) {
          counts.put(val, counts.get(val) + 1);
        } else {
          counts.put(val, 1);
        }
      }

      return counts;
    }

    public Boolean isRoyal(Card[] cards) {
      Boolean hasAce = false;
      Boolean hasKing = false;

      for (Card card : cards) {
        if (card.value == 14) {
          hasAce = true;
        } else if (card.value == 13) {
          hasKing = true;
        }
      }
      return hasAce && hasKing;
    }

    public Boolean allSameSuit(Card[] hand) {
      Boolean allSameSuit = true;
      char suit = hand[0].suit;

      for (Card card : hand) {
        int cardVal = card.altValue != 0 ? card.altValue : card.value;
        comparisonCards.add(cardVal);
        if (card.suit != suit) {
          comparisonCards.clear();
          return false;
        }
      }

      Collections.sort(comparisonCards);
      Collections.reverse(comparisonCards);

      return true;
    }

    public Boolean inARow(Card[] hand) {
      Boolean inARow = true;
      Boolean aceLow = false;

      // insertion sort
      for (int i = 0; i < hand.length - 1; i++) {
        for (int j = i; j < hand.length; j++) {
          Card c1 = hand[i];
          Card c2 = hand[j];
          if (c1.value > c2.value) {
            // swap
            Card tmp = c1;
            hand[i] = c2;
            hand[j] = tmp;
          }
        }
      }

      for (int i = 0; i < hand.length - 1; i++) {
        if (hand[i].value + 1 == hand[i+1].value) {
          // we good
        } else if (hand[i].altValue + 1 == hand[i+1].value) {
          aceLow = true;
          // but we still good
        } else {
          return false;
        }
      }

      int maxCardPos = hand.length - 1;

      if (aceLow) {
        maxCardPos = hand.length - 2;
      }

      comparisonCards.add(hand[maxCardPos].value);

      return true;
    }

    public Boolean is4Kind(Card[] hand) {
      int fourVal = 0;
      int kicker = 0;

      for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
        int cardVal = entry.getKey();
        if (entry.getValue() == 4) {
          fourVal = cardVal;
        } else {
          kicker = cardVal;
        }
      }

      if (fourVal != 0 && kicker != 0) {
        comparisonCards.add(fourVal);
        comparisonCards.add(kicker);
        return true;
      }

      return false;
    }

    public Boolean isFullHouse(Card[] hand) {
      int threeVal = 0;
      int twoVal = 0;

      for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
        int cardVal = entry.getKey();

        if (entry.getValue() == 3) {
          threeVal = cardVal;
        } else if (entry.getValue() == 2) {
          twoVal = cardVal;
        }
      }

      if (threeVal != 0 && twoVal != 0) {
        comparisonCards.add(threeVal);
        comparisonCards.add(twoVal);
        return true;
      }

      return false;
    }

    public Boolean is3Kind(Card[] hand) {
      int threeVal = 0;
      int kicker1 = 0;
      int kicker2 = 0;

      for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
        int cardVal = entry.getKey();

        if (entry.getValue() == 3) {
          threeVal = cardVal;
        } else if (kicker1 == 0) {
          kicker1 = cardVal;
        } else if (kicker2 == 0) {
          kicker2 = cardVal;
        }
      }

      if (threeVal != 0) {
        comparisonCards.add(threeVal);
        if (kicker1 < kicker2) {
          int tmp = kicker1;
          kicker1 = kicker2;
          kicker2 = tmp;
        }
        comparisonCards.add(kicker1);
        comparisonCards.add(kicker2);
        return true;
      }

      return false;
    }

    public Boolean is2Pair(Card[] hand) {
      int pair1Val = 0;
      int pair2Val = 0;
      int kicker = 0;

      for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
        int cardVal = entry.getKey();
        if (entry.getValue() == 2 && pair1Val == 0) {
          pair1Val = cardVal;
        } else if (entry.getValue() ==2 && pair2Val == 0) {
          pair2Val = cardVal;
        } else if (kicker == 0) {
          kicker = cardVal;
        }
      }

      if (pair1Val != 0 && pair2Val != 0) {
        if (pair1Val < pair2Val) {
          int tmp = pair1Val;
          pair1Val = pair2Val;
          pair2Val = tmp;
        }

        comparisonCards.add(pair1Val);
        comparisonCards.add(pair2Val);
        comparisonCards.add(kicker);

        return true;
      }

      return false;
    }

    public Boolean is1Pair(Card[] hand) {
      int pair1Val = 0;
      int[] kickers = new int[PokerHand.HAND_SIZE];
      int i = 0; // counter for kickers

      for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
        int cardVal = entry.getKey();

        if (entry.getValue() == 2) {
          pair1Val = cardVal;
        } else {
          kickers[i] = cardVal;
          i++;
        }
      }

      if (pair1Val != 0) {
        Arrays.sort(kickers);

        comparisonCards.add(pair1Val);
        for (int k : kickers) {
          comparisonCards.add(k);
        }

        return true;
      }

      return false;
    }

    public Boolean isHighCard(Card[] hand) {
      for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
        int cardVal = entry.getKey();
        comparisonCards.add(cardVal);
      }

      Collections.sort(comparisonCards);
      Collections.reverse(comparisonCards);

      return true;
    }
  }

  public enum RankType {
    ROYAL_FLUSH("Royal Flush", 10),
    STRAIGHT_FLUSH("Straight Flush", 9),
    FOUR_OF_A_KIND("Four of a Kind", 8),
    FULL_HOUSE("Full House", 7),
    FLUSH("Flush", 6),
    STRAIGHT("Straight", 5),
    THREE_OF_A_KIND("3 of a Kind", 4),
    TWO_PAIR("2 Pair", 3),
    ONE_PAIR("1 Pair", 2),
    HIGH_CARD("High Card", 1);

    String displayName;
    int rankOrder;

    RankType(String displayName, int rankOrder) {
      this.displayName = displayName;
      this.rankOrder = rankOrder;
    }
  }

  class Card {
    int value;
    int altValue = 0;
    char suit;
    String display;

    public Card(String card) {
      this.display = card;
      this.suit = card.charAt(1);
      this.value = getValue(card.charAt(0));
    }

    public int getValue(char c) {
      int cardValue = 0;

      if (c >= '2' && c <= '9') {
        cardValue = Character.getNumericValue(c);
      } else {
        switch (c) {
          case 'T': cardValue = 10; break;
          case 'J': cardValue = 11; break;
          case 'Q': cardValue = 12; break;
          case 'K': cardValue = 13; break;
          case 'A': cardValue = 14; altValue = 1; break;
        }
      }
      return cardValue;
    }

    public String toString() {
      return display;
    }
  }
}
