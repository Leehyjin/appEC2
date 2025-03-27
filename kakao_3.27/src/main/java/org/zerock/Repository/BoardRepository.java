package org.zerock.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.Repository.search.BoardSearch;
import org.zerock.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {
}
