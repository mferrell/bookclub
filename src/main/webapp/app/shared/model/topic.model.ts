import { IComment } from 'app/shared/model/comment.model';

export interface ITopic {
  id?: number;
  title?: string;
  comments?: IComment[];
  userId?: number;
  bookId?: number;
  chapterId?: number;
}

export class Topic implements ITopic {
  constructor(
    public id?: number,
    public title?: string,
    public comments?: IComment[],
    public userId?: number,
    public bookId?: number,
    public chapterId?: number
  ) {}
}
