package study.board.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import study.board.controller.api.BoardApiController;
import study.board.controller.api.dto.BoardSaveDto;
import study.board.entity.Board;
import study.board.service.BoardService;

@ExtendWith(MockitoExtension.class)
class BoardApiControllerTest {

    @InjectMocks
    private BoardApiController boardApiController;

    @Mock
    private BoardService boardService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(boardApiController).build();
    }

    @DisplayName("게시판 등록 성공")
    @Test
    void saveBoardSuccess() {
        Board board = Board.createBoard("test", "test");


    }

}