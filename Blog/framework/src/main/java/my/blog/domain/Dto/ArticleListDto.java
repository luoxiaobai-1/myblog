package my.blog.domain.Dto;

import lombok.Data;

@Data
public class ArticleListDto {
  private   Integer pageNum;
  private   Integer pageSize;
  private   String title;
  private   String summary;
}
