package controller;

import connector.ConnectionMaker;
import model.BoardDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BoardController {
    private Connection connection;

    public BoardController(ConnectionMaker connectionMaker) {
        connection = connectionMaker.makeConnection();
    }

    public List<BoardDTO> selectAll() {
        List<BoardDTO> list = new ArrayList<>();
        String query = "SELECT * FROM board as b INNER JOIN user ON b.writer_id = user.id";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BoardDTO boardDTO = new BoardDTO();
                boardDTO.setId(resultSet.getInt("b.id"));
                boardDTO.setTitle(resultSet.getString("title"));
                boardDTO.setContent(resultSet.getString("content"));
                boardDTO.setEntryDate(resultSet.getTimestamp("entry_date"));
                boardDTO.setModifyDate(resultSet.getTimestamp("modify_date"));
                boardDTO.setWriterId(resultSet.getInt("writer_id"));
                boardDTO.setNickname(resultSet.getString("nickname"));

                list.add(boardDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public BoardDTO selectOne(int id) {
        String query = "SELECT * FROM board INNER JOIN user ON board.writer_id = user.id WHERE board.id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BoardDTO boardDTO = new BoardDTO();
                boardDTO.setId(resultSet.getInt("id"));
                boardDTO.setTitle(resultSet.getString("title"));
                boardDTO.setContent(resultSet.getString("content"));
                boardDTO.setEntryDate(resultSet.getTimestamp("entry_date"));
                boardDTO.setModifyDate(resultSet.getTimestamp("modify_date"));
                boardDTO.setWriterId(resultSet.getInt("writer_id"));
                boardDTO.setNickname(resultSet.getString("nickname"));

                return boardDTO;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void insert(BoardDTO boardDTO) {
        String query = "INSERT INTO board(title, content, writer_id) VALUES(?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, boardDTO.getTitle());
            preparedStatement.setString(2, boardDTO.getContent());
            preparedStatement.setInt(3, boardDTO.getWriterId());

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(BoardDTO boardDTO) {
        String query = "UPDATE board SET title = ?, content = ?, modify_date = NOW() WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, boardDTO.getTitle());
            preparedStatement.setString(2, boardDTO.getContent());
            preparedStatement.setInt(3, boardDTO.getId());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM board WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
