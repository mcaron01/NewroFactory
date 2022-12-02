export class Chapter {
  id?: number;
  name: string;
  children?: Chapter[];

  constructor(id: number,name: string, children?: Chapter[]) {
    this.id = id;
    this.name = name;
    this.children = children;
  }
}
