import { Moment } from 'moment';

export interface IComment {
  id?: number;
  text?: string;
  timestamp?: Moment;
  userId?: number;
  chapterId?: number;
  topicId?: number;
}

export class Comment implements IComment {
  constructor(
    public id?: number,
    public text?: string,
    public timestamp?: Moment,
    public userId?: number,
    public chapterId?: number,
    public topicId?: number
  ) {}
}
