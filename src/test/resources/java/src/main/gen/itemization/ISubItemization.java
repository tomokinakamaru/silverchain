package itemization;

interface ISubItemization<INNER, ITEM> {

  itemization.EmptySubItemization<itemization.SubItemization<INNER, ITEM>> begin();

  INNER end();

  itemization.SubItemization<INNER, ITEM> item(ITEM item);
}
