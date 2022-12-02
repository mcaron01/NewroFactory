import { Injectable } from '@angular/core';
import { MatPaginatorIntl } from '@angular/material/paginator';
import { Subject } from 'rxjs';

@Injectable()
export class MyCustomPaginatorIntl implements MatPaginatorIntl {
  changes = new Subject<void>();

  itemsPerPageLabel: string = 'Nombre par page :';
  nextPageLabel: string = 'Page suivante';
  previousPageLabel: string = 'Page précédente';
  firstPageLabel: string = 'Première page';
  lastPageLabel: string = 'Dernière page';

  getRangeLabel(page: number, pageSize: number, length: number): string {
    if (length === 0) {
      return '0 sur 0';
    }
    const amountPages = Math.ceil(length / pageSize);
    const min = 1 + page * pageSize;
    const max = Math.min((page + 1) * pageSize, length);
    return `${min}-${max} sur ${length}`;
  }
}
