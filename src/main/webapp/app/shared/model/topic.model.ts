import { IComment } from 'app/shared/model/comment.model';

export interface ITopic {
  id?: number;
  title?: string;
  userId?: number;
  comments?: IComment[];
  bookId?: number;
  chapterId?: number;
}

export class Topic implements ITopic {
  constructor(
    public id?: number,
    public title?: string,
    public userId?: number,
    public comments?: IComment[],
    public bookId?: number,
    public chapterId?: number
  ) {}
}
