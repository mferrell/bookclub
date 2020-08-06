import { IComment } from 'app/shared/model/comment.model';
import { ITopic } from 'app/shared/model/topic.model';

export interface IChapter {
  id?: number;
  title?: string;
  userId?: number;
  comments?: IComment[];
  topics?: ITopic[];
  bookId?: number;
}

export class Chapter implements IChapter {
  constructor(
    public id?: number,
    public title?: string,
    public userId?: number,
    public comments?: IComment[],
    public topics?: ITopic[],
    public bookId?: number
  ) {}
}
