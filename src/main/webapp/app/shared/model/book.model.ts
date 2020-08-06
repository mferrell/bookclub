import { ITopic } from 'app/shared/model/topic.model';
import { IChapter } from 'app/shared/model/chapter.model';

export interface IBook {
  id?: number;
  title?: string;
  url?: string;
  image?: string;
  userId?: number;
  topics?: ITopic[];
  chapters?: IChapter[];
}

export class Book implements IBook {
  constructor(
    public id?: number,
    public title?: string,
    public url?: string,
    public image?: string,
    public userId?: number,
    public topics?: ITopic[],
    public chapters?: IChapter[]
  ) {}
}
