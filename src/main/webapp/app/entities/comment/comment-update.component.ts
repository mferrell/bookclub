import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IComment, Comment } from 'app/shared/model/comment.model';
import { CommentService } from './comment.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IChapter } from 'app/shared/model/chapter.model';
import { ChapterService } from 'app/entities/chapter/chapter.service';
import { ITopic } from 'app/shared/model/topic.model';
import { TopicService } from 'app/entities/topic/topic.service';

type SelectableEntity = IUser | IChapter | ITopic;

@Component({
  selector: 'jhi-comment-update',
  templateUrl: './comment-update.component.html',
})
export class CommentUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  chapters: IChapter[] = [];
  topics: ITopic[] = [];
  timestampDp: any;

  editForm = this.fb.group({
    id: [],
    text: [],
    timestamp: [],
    userId: [],
    chapterId: [],
    topicId: [],
  });

  constructor(
    protected commentService: CommentService,
    protected userService: UserService,
    protected chapterService: ChapterService,
    protected topicService: TopicService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comment }) => {
      this.updateForm(comment);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.chapterService.query().subscribe((res: HttpResponse<IChapter[]>) => (this.chapters = res.body || []));

      this.topicService.query().subscribe((res: HttpResponse<ITopic[]>) => (this.topics = res.body || []));
    });
  }

  updateForm(comment: IComment): void {
    this.editForm.patchValue({
      id: comment.id,
      text: comment.text,
      timestamp: comment.timestamp,
      userId: comment.userId,
      chapterId: comment.chapterId,
      topicId: comment.topicId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const comment = this.createFromForm();
    if (comment.id !== undefined) {
      this.subscribeToSaveResponse(this.commentService.update(comment));
    } else {
      this.subscribeToSaveResponse(this.commentService.create(comment));
    }
  }

  private createFromForm(): IComment {
    return {
      ...new Comment(),
      id: this.editForm.get(['id'])!.value,
      text: this.editForm.get(['text'])!.value,
      timestamp: this.editForm.get(['timestamp'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      chapterId: this.editForm.get(['chapterId'])!.value,
      topicId: this.editForm.get(['topicId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComment>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
