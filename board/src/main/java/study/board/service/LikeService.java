package study.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.entity.Board;
import study.board.entity.Like;
import study.board.entity.Member;
import study.board.repository.LikeRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;



    /**
     *
     * @param board
     * @param member
     * @return  true:=좋아요를 새로 추가
     *          false:=기존의 좋아요 삭제
     */
    public boolean addOrDeleteLike(Board board, Member member) {
        //좋아요가 이미 존재하는 경우
        if (likeRepository.existsLikeByBoardAndMember(board, member)) {
            likeRepository.deleteByBoardAndMember(board, member);
            return false;
        } else { // 좋아요를 새로 추가하는 경우
            Like newLike = Like.createLike(board, member);
            likeRepository.save(newLike);
            return true;
        }
    }
    public boolean addOrDeleteLikeV2(Board board, Member member) {
        Like like = likeRepository.findTop1ByBoardAndMember(board, member)
                .orElse(null);
        if (like == null) {
            Like newLike = Like.createLike(board, member);
            likeRepository.save(newLike);
            return true;
        }
        else{
            likeRepository.delete(like);
            return false;
        }
    }
}
