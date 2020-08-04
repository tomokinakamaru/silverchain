package itemization;

interface IEmptySubItemization<INNER> {

  <ITEM> itemization.SubItemization<INNER, ITEM> item(ITEM item);
}
