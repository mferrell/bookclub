import { IComment } from 'app/shared/model/comment.model';
import { ITopic } from 'app/shared/model/topic.model';

export interface IChapter {
  id?: number;
  title?: string;
  comments?: IComment[];
  topics?: ITopic[];
  userId?: number;
  bookId?: number;
}

export class Chapter implements IChapter {
  constructor(
    public id?: number,
    public title?: string,
    public comments?: IComment[],
    public topics?: ITopic[],
    public userId?: number,
    public bookId?: number
  ) {}
}
