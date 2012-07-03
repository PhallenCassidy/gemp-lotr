package com.gempukku.lotro.db;

import com.gempukku.lotro.logic.vo.LotroDeck;
import com.gempukku.lotro.tournament.TournamentPlayerDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DbTournamentPlayerDAO implements TournamentPlayerDAO {
    private DbAccess _dbAccess;

    public DbTournamentPlayerDAO(DbAccess dbAccess) {
        _dbAccess = dbAccess;
    }

    @Override
    public void addPlayer(String tournamentId, String playerName, LotroDeck deck) {
        try {
            Connection conn = _dbAccess.getDataSource().getConnection();
            try {
                PreparedStatement statement = conn.prepareStatement("insert into tournament_player (tournament_id, player, deck_name, deck) values (?, ?, ?, ?)");
                try {
                    statement.setString(1, tournamentId);
                    statement.setString(2, playerName);
                    statement.setString(3, deck.getDeckName());
                    statement.setString(4, DeckSerialization.buildContentsFromDeck(deck));
                    statement.execute();
                } finally {
                    statement.close();
                }
            } finally {
                conn.close();
            }
        } catch (SQLException exp) {
            throw new RuntimeException(exp);
        }
    }

    @Override
    public void dropPlayer(String tournamentId, String playerName) {
        try {
            Connection conn = _dbAccess.getDataSource().getConnection();
            try {
                PreparedStatement statement = conn.prepareStatement("update tournament_player set dropped=true where tournament_id=? and player=?");
                try {
                    statement.setString(1, tournamentId);
                    statement.setString(2, playerName);
                    statement.executeUpdate();
                } finally {
                    statement.close();
                }
            } finally {
                conn.close();
            }
        } catch (SQLException exp) {
            throw new RuntimeException(exp);
        }
    }

    @Override
    public Map<String, LotroDeck> getPlayers(String tournamentId) {
        try {
            Connection connection = _dbAccess.getDataSource().getConnection();
            try {
                PreparedStatement statement = connection.prepareStatement("select player, deck_name, deck from tournament_player where tournament_id=?");
                try {
                    statement.setString(1, tournamentId);
                    ResultSet rs = statement.executeQuery();
                    try {
                        Map<String, LotroDeck> result = new HashMap<String, LotroDeck>();
                        while (rs.next()) {
                            String player = rs.getString(1);
                            String deckName = rs.getString(2);
                            String contents = rs.getString(3);

                            result.put(player, DeckSerialization.buildDeckFromContents(deckName, contents));
                        }
                        return result;
                    } finally {
                        rs.close();
                    }
                } finally {
                    statement.close();
                }
            } finally {
                connection.close();
            }
        } catch (SQLException exp) {
            throw new RuntimeException(exp);
        }
    }

    @Override
    public Set<String> getDroppedPlayers(String tournamentId) {
        try {
            Connection connection = _dbAccess.getDataSource().getConnection();
            try {
                PreparedStatement statement = connection.prepareStatement("select player from tournament_player where tournament_id=? and dropped=true");
                try {
                    statement.setString(1, tournamentId);
                    ResultSet rs = statement.executeQuery();
                    try {
                        Set<String> result = new HashSet<String>();
                        while (rs.next()) {
                            result.add(rs.getString(1));
                        }
                        return result;
                    } finally {
                        rs.close();
                    }
                } finally {
                    statement.close();
                }
            } finally {
                connection.close();
            }
        } catch (SQLException exp) {
            throw new RuntimeException(exp);
        }
    }

    @Override
    public LotroDeck getPlayerDeck(String tournamentId, String playerName) {
        try {
            Connection connection = _dbAccess.getDataSource().getConnection();
            try {
                PreparedStatement statement = connection.prepareStatement("select deck_name, deck from tournament_player where tournament_id=? and player=?");
                try {
                    statement.setString(1, tournamentId);
                    statement.setString(2, playerName);
                    ResultSet rs = statement.executeQuery();
                    try {
                        if (rs.next())
                            return DeckSerialization.buildDeckFromContents(rs.getString(1), rs.getString(2));
                        else
                            return null;
                    } finally {
                        rs.close();
                    }
                } finally {
                    statement.close();
                }
            } finally {
                connection.close();
            }
        } catch (SQLException exp) {
            throw new RuntimeException(exp);
        }
    }
}
