package controller;

import connector.ConnectionMaker;
import model.ReplyDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReplyController {
    private Connection connection;

    public ReplyController(ConnectionMaker connectionMaker) {
        connection = connectionMaker.makeConnection();
    }

    public List<ReplyDTO> selectAll(int boardId) {
        List<ReplyDTO> list = new ArrayList<>();

        String query = "SELECT * FROM reply INNER JOIN user ON reply.writer_id = user.id WHERE board_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, boardId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ReplyDTO replyDTO = new ReplyDTO();

                replyDTO.setId(resultSet.getInt("id"));
                replyDTO.setContent(resultSet.getString("content"));
                replyDTO.setEntryDate(resultSet.getTimestamp("entry_date"));
                replyDTO.setModifyDate(resultSet.getTimestamp("modify_date"));
                replyDTO.setWriterId(resultSet.getInt("writer_id"));
                replyDTO.setBoardId(resultSet.getInt("board_id"));
                replyDTO.setNickname(resultSet.getString("nickname"));

                list.add(replyDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insert(ReplyDTO replyDTO) {
        String query = "INSERT INTO reply(content, writer_id, board_id) VALUES(?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, replyDTO.getContent());
            preparedStatement.setInt(2, replyDTO.getWriterId());
            preparedStatement.setInt(3, replyDTO.getBoardId());

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void update(ReplyDTO replyDTO) {
        String query = "UPDATE reply SET content = ?, modify_date = NOW() WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, replyDTO.getContent());
            preparedStatement.setInt(2, replyDTO.getId());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM reply WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
















